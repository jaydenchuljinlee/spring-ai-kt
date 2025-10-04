package com.example.ai.memory.memory

import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CustomMessageWindowChatMemory(
    private val chatMemoryRepository: ChatMemoryRepository,
    @Value("\${chat.memory.max-messages}")
    var maxMessages: Int
): ChatMemory {

    override fun add(conversationId: String, messages: List<Message>) {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        require(messages.isNotEmpty()) { "messages cannot be empty" }
        require(messages.none { it == null }) { "messages cannot contain null elements" }

        val memoryMessages = chatMemoryRepository.findByConversationId(conversationId)
        val processedMessages = this.process(memoryMessages, messages);
        chatMemoryRepository.saveAll(conversationId, processedMessages.toList())
    }

    override fun get(conversationId: String): List<Message> {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        return chatMemoryRepository.findByConversationId(conversationId)
    }

    override fun clear(conversationId: String) {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        chatMemoryRepository.deleteByConversationId(conversationId)
    }

    private fun process(memoryMessages: List<Message>, newMessages: List<Message>): List<Message> {
        val memoryMessagesSet = memoryMessages.toSet()
        val hasNewSystemMessage = newMessages.any { it is SystemMessage && it !in memoryMessagesSet }

        val processed = memoryMessages
            .filterNot { hasNewSystemMessage && it is SystemMessage }
            .toMutableList()
            .apply { addAll(newMessages) }

        if (processed.size <= maxMessages) return processed

        var removed = 0
        val overLimit = processed.size - maxMessages
        return processed.filter {
            if (it !is SystemMessage && removed < overLimit) {
                removed++
                false
            } else true
        }
    }
}