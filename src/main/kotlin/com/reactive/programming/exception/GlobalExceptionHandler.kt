package com.reactive.programming.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFound::class)
    fun handleUserNotFound(exc: UserNotFound) = ResponseEntity.status(
        HttpStatus.NOT_FOUND
    ).body("User ${exc.id} could not be found!")

    @ExceptionHandler(UserNotCreated::class)
    fun handleUserNotCreated(exc: UserNotCreated) = ResponseEntity.status(
        HttpStatus.BAD_REQUEST
    ).body("User could not be created!")
}