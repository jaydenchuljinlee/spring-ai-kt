package com.example.ai.memory.service

import com.example.ai.chat.service.ChatClientService
import com.example.ai.memory.repository.JdbcChatRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.stereotype.Service

@Service("jdbcChatMemoryService")
class JdbcChatMemoryService(
    private val openAiChatClientService: ChatClientService,
    private val jdbcChatRepository: JdbcChatRepository
): ChatMemoryService {
    override fun createConversation(userMessage: String): String {
        val message = openAiChatClientService.sendMessage(userMessage)
        return jdbcChatRepository.createMessage(message)
    }

    override fun sendMessage(conversationId: String, userMessage: String): Message {
        val conversationMessages = jdbcChatRepository.getMessage(conversationId)
        val message = openAiChatClientService.sendMessageWithConversation(userMessage, conversationMessages)
        jdbcChatRepository.addMessage(conversationId, message)
        return message
    }
}