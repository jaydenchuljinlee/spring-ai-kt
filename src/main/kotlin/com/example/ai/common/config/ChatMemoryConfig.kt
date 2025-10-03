package com.example.ai.common.config

import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatMemoryConfig {
    @Bean
    fun inMemoryChatMemoryRepository(): ChatMemoryRepository {
        return InMemoryChatMemoryRepository()
    }

}