package com.reactive.programming.controller

import com.reactive.programming.model.UserInput
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

    @PostMapping("/user")
    fun saveUser(@RequestBody userInput: UserInput): ResponseEntity<Mono<UserInput>> {
        return ResponseEntity.ok(userRepository.insert(userInput))
    }

}