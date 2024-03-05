package com.reactive.programming.model


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class UserInput(
    @Id
    var id: Int?,
    var firstName:String,
    var lastName:String,
    var age:Int,
    var resgistrationDate: Instant = Instant.now()
)
