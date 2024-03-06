package com.reactive.programming.exception

data class UserNotFound(override val message: String?, val id: Long) : Exception(message)
