package com.reactive.programming.model


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserInput(
    @Id
    var id: String?,
    var name: String,
    var username: String,
    var email: String
) {
    fun toUserInputResponse(): UserInputResponse = UserInputResponse(
        this.name,
        this.username,
        this.email
    )
}
