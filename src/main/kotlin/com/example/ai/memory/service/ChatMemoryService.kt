package com.example.ai.memory.service

import org.springframework.ai.chat.messages.Message

interface ChatMemoryService {
    fun createConversation(userMessage: String): String
    fun sendMessage(conversationId: String, message: String): Message
}