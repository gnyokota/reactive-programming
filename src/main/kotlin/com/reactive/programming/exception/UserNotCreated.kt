package com.reactive.programming.exception

data class UserNotCreated(override val message: String?): Exception(message)
