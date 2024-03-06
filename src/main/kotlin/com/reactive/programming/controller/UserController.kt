package com.reactive.programming.controller

import com.reactive.programming.exception.UserNotCreated
import com.reactive.programming.exception.UserNotFound
import com.reactive.programming.model.UserInput
import com.reactive.programming.model.UserInputResponse
import com.reactive.programming.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@RequestMapping("/api/v1")
class UserController(val userRepository: UserRepository, val userWebClient: WebClient) {

    private val logger =  LoggerFactory.getLogger(UserController::class.java)
    @GetMapping("/users")
    fun getAllUser(): ResponseEntity<Flux<UserInput>> {
        return ResponseEntity.ok(userRepository.findAll())
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<Mono<UserInputResponse>> {
        val foundUser = userRepository.findById(id)
        return ResponseEntity.ok(foundUser.map {
            it.toUserInputResponse()
        }.switchIfEmpty { Mono.error(UserNotFound("User $id could not be found!", id.toLong())) }
            .doFirst{logger.info("Retrieving user with id:$id")}
            .doOnError{ exc -> logger.error("Something went wrong while retrieveing user: $id, ${exc.message}")}
            .doFinally { signalType -> logger.info("Finalized retrieving user: $signalType") })
    }

    @PostMapping("/user")
    fun saveUser(@RequestBody userInput: UserInput): ResponseEntity<Mono<UserInputResponse>> {
        val createdUser = userRepository.insert(userInput).map{
            it.toUserInputResponse()
        }.onErrorMap {  exc-> UserNotCreated("User could not be created: ${exc.message}!")  }
        return ResponseEntity.ok(createdUser)
    }

    @PostMapping("/users/ext")
    fun saveExternalUsers(): ResponseEntity<Flux<UserInputResponse>> {
        val externalUsers = userWebClient.get()
            .uri("/users")
            .retrieve()
            .bodyToFlux(UserInput::class.java)

        val createdUser = userRepository.insert(externalUsers).map{
            it.toUserInputResponse()
        }.doOnError {  exc-> UserNotCreated("User could not be created: ${exc.message}!")  }
        return ResponseEntity.ok(createdUser)
    }

    @DeleteMapping("/users/{id}")
    fun deleteUserById(@PathVariable id:String): ResponseEntity<Any>{
        return ResponseEntity.ok(userRepository.deleteById(id).doOnError {
            exc -> logger.error("User $id could not be deleted: ${exc.message}")
        }.doFinally{ signalType->
            logger.info("User $id successfully deleted: $signalType")
        })
    }
}