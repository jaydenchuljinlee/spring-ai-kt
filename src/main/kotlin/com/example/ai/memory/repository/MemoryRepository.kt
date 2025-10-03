package com.example.ai.memory.repository

import org.springframework.ai.chat.messages.Message

interface MemoryRepository {
    fun getMessage(conversationId: String): List<Message>

    fun createMessage(message: Message): String

    fun addMessage(conversationId: String, message: Message)
}