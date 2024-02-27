package com.reactive.programming.repository

import com.reactive.programming.model.UserInput
import org.springframework.data.cassandra.repository.CassandraRepository

interface UserRepository:CassandraRepository<UserInput,Int> {
}