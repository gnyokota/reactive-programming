package com.reactive.programming.controller

import com.reactive.programming.model.UserInput
import com.reactive.programming.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(val userRepository: UserRepository) {
    @GetMapping("/users")
    fun getAllUser(): ResponseEntity<List<UserInput>> {
        return ResponseEntity.ok(userRepository.findAll())
    }

    @PostMapping("/user")
    fun saveUser(@RequestBody userInput: UserInput): ResponseEntity<UserInput> {
        return ResponseEntity.ok(userRepository.insert(userInput))
    }

}