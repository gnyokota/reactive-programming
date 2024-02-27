package com.reactive.programming.model

import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.Table
import java.time.Instant
@Table
data class UserInput(
    @PrimaryKey
    var id: Int?,
    var firstName:String,
    var lastName:String,
    var age:Int,
    var resgistrationDate: Instant = Instant.now()
)
