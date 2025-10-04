package com.example.ai.memory.memory

import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.util.*

@Component
class CustomMessageWindowChatMemory(
    private val chatMemoryRepository: ChatMemoryRepository
): ChatMemory {
    val DEFAULT_MAX_MESSAGES: Int = 20
    var maxMessages = DEFAULT_MAX_MESSAGES


    override fun add(conversationId: String, messages: MutableList<Message>) {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        require(messages.isNotEmpty()) { "messages cannot be empty" }
        require(messages.none { it == null }) { "messages cannot contain null elements" }

        val memoryMessages = chatMemoryRepository.findByConversationId(conversationId)
        val processedMessages = this.process(memoryMessages, messages);
        chatMemoryRepository.saveAll(conversationId, processedMessages)
    }

    override fun get(conversationId: String): MutableList<Message> {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        return chatMemoryRepository.findByConversationId(conversationId)
    }

    override fun clear(conversationId: String) {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        chatMemoryRepository.deleteByConversationId(conversationId)
    }

    private fun process(memoryMessages: List<Message>, newMessages: List<Message>): List<Message> {
        val processedMessages = mutableListOf<Message>()
        val memoryMessagesSet = mutableSetOf<Message>()
        memoryMessages.stream().map { memoryMessagesSet.add(it) }

        var var10000 = newMessages.stream()
        val hasNewSystemMessage = var10000.filter { obj: Message -> SystemMessage::class.java.isInstance(obj) }
            .anyMatch { !memoryMessagesSet.contains(it) }
        var10000 =
            memoryMessages.stream().filter { messagex -> !hasNewSystemMessage || messagex !is SystemMessage }
        Objects.requireNonNull<List<Message>>(processedMessages)
        var10000.forEach(processedMessages::add)
        processedMessages.addAll(newMessages)
        if (processedMessages.size <= this.maxMessages) {
            return processedMessages
        } else {
            val messagesToRemove = processedMessages.size - this.maxMessages
            val trimmedMessages = mutableListOf<Message>()
            var removed = 0

            for (message in processedMessages) {
                if (message !is SystemMessage && removed < messagesToRemove) {
                    ++removed
                } else {
                    trimmedMessages.add(message)
                }
            }

            return trimmedMessages
        }
    }
}