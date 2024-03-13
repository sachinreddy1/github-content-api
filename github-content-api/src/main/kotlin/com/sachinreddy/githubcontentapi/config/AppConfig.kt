package com.sachinreddy.githubcontentapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {
    @Bean
    fun webClient(): WebClient = WebClient.create()
}
