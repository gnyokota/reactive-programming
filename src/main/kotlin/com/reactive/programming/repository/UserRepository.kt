package com.reactive.programming.repository

import com.reactive.programming.model.UserInput
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : ReactiveMongoRepository<UserInput, String> {
}