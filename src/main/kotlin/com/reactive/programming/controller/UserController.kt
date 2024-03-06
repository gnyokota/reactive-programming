package com.reactive.programming.controller

import com.reactive.programming.exception.UserNotFound
import com.reactive.programming.model.UserInput
import com.reactive.programming.model.UserInputResponse
import com.reactive.programming.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@RestController
@RequestMapping("/api/v1")
class UserController(val userRepository: UserRepository) {
    @GetMapping("/users")
    fun getAllUser(): ResponseEntity<Flux<UserInput>> {
        return ResponseEntity.ok(userRepository.findAll())
    }

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: String): ResponseEntity<Mono<UserInputResponse>> {
        val foundUser = userRepository.findById(id)
        return ResponseEntity.ok(foundUser.map {
            it.toUserInputResponse()
        }.switchIfEmpty { Mono.error(UserNotFound("User $id could not be found!", id.toLong())) })
    }

    @PostMapping("/user")
    fun saveUser(@RequestBody userInput: UserInput): ResponseEntity<Mono<UserInput>> {
        return ResponseEntity.ok(userRepository.insert(userInput))
    }

}