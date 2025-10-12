package com.example.ai.memory.repository

import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JdbcChatRepository(
    private val jdbcChatMemoryRepository: ChatMemoryRepository
): MemoryRepository {
    override fun getMessage(conversationId: String): List<Message> {
        return jdbcChatMemoryRepository.findByConversationId(conversationId)
    }

    override fun createMessage(message: Message): String {
        val conversationId = UUID.randomUUID().toString()
        jdbcChatMemoryRepository.saveAll(conversationId, listOf(message))
        return conversationId
    }

    override fun addMessage(conversationId: String, message: Message) {
        jdbcChatMemoryRepository.saveAll(conversationId, listOf(message))
    }
}