package com.reactive.programming.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class UserClientConfig {
    private val logger =  LoggerFactory.getLogger(UserClientConfig::class.java)

    @Bean
    fun userWebClient(builder: WebClient.Builder):WebClient =
        builder.baseUrl("https://jsonplaceholder.typicode.com").build()
}