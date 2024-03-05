package com.reactive.programming.controller

import com.reactive.programming.model.UserInput
import com.reactive.programming.model.UserInputResponse
import com.reactive.programming.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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
        })
    }

    @PostMapping("/user")
    fun saveUser(@RequestBody userInput: UserInput): ResponseEntity<Mono<UserInput>> {
        return ResponseEntity.ok(userRepository.insert(userInput))
    }

}