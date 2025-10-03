package com.example.ai.memory.service

import com.example.ai.chat.service.ChatClientService
import com.example.ai.memory.repository.MemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.stereotype.Service

@Service
class InMemoryService(
    private val openAiChatClientService: ChatClientService,
    private val inMemoryChatRepository: MemoryRepository
): ChatMemoryService {
    override fun createConversation(userMessage: String): String {
        val message = openAiChatClientService.sendMessage(userMessage)
        return inMemoryChatRepository.createMessage(message)
    }

    override fun sendMessage(conversationId: String, userMessage: String): Message {
        val conversationMessages = inMemoryChatRepository.getMessage(conversationId)
        val message = openAiChatClientService.sendMessageWithConversation(userMessage, conversationMessages)
        inMemoryChatRepository.addMessage(conversationId, message)
        return message
    }
}