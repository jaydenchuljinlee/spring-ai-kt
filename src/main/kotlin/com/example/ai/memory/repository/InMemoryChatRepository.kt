package com.example.ai.memory.repository

import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class InMemoryChatRepository(
    private val inMemoryChatMemoryRepository: ChatMemoryRepository
): MemoryRepository {
    override fun getMessage(conversationId: String): List<Message> {
        return inMemoryChatMemoryRepository.findByConversationId(conversationId)
    }

    override fun createMessage(message: Message): String {
        val conversationId = UUID.randomUUID().toString()
        inMemoryChatMemoryRepository.saveAll(conversationId, listOf(message))
        return conversationId
    }

    override fun addMessage(conversationId: String, message: Message) {
        inMemoryChatMemoryRepository.saveAll(conversationId, listOf(message))
    }

}