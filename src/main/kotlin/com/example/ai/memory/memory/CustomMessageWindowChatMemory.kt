package com.example.ai.memory.memory

import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.memory.ChatMemoryRepository
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.stereotype.Component

@Component
class CustomMessageWindowChatMemory(
    private val chatMemoryRepository: ChatMemoryRepository
): ChatMemory {

    companion object {
        private const val DEFAULT_MAX_MESSAGES = 20
    }

    var maxMessages: Int = DEFAULT_MAX_MESSAGES

    override fun add(conversationId: String, messages: MutableList<Message>) {
        require(conversationId.isNotBlank()) { "conversationId cannot be null or empty" }
        require(messages.isNotEmpty()) { "messages cannot be empty" }
        require(messages.none { it == null }) { "messages cannot contain null elements" }

        val memoryMessages = chatMemoryRepository.findByConversationId(conversationId)
        val processedMessages = this.process(memoryMessages, messages);
        chatMemoryRepository.saveAll(conversationId, processedMessages.toList())
        // chatMemoryRepository.saveAll(conversationId, processedMessages)
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
        // Java식 로직 변경
//         val processedMessages = mutableListOf<Message>()
//
//         val memoryMessagesSet = mutableSetOf<Message>()
//         memoryMessages.stream().map { memoryMessagesSet.add(it) }
//
//        var var10000 = newMessages.stream()
//        val hasNewSystemMessage = var10000.filter { obj: Message -> SystemMessage::class.java.isInstance(obj) }
//            .anyMatch { !memoryMessagesSet.contains(it) }
//        var10000 = memoryMessages.stream().filter { messagex -> !hasNewSystemMessage || messagex !is SystemMessage }
//
//        Objects.requireNonNull<List<Message>>(processedMessages)
//        var10000.forEach(processedMessages::add)
//        processedMessages.addAll(newMessages)
//        if (processedMessages.size <= this.maxMessages) {
//            return processedMessages
//        } else {
//            val messagesToRemove = processedMessages.size - this.maxMessages
//            val trimmedMessages = mutableListOf<Message>()
//            var removed = 0
//
//            for (message in processedMessages) {
//                if (message !is SystemMessage && removed < messagesToRemove) {
//                    ++removed
//                } else {
//                    trimmedMessages.add(message)
//                }
//            }
//
//            return trimmedMessages
//        }

        val memoryMessagesSet = memoryMessages.toMutableSet()
        val hasNewSystemMessage = newMessages.any { it is SystemMessage && it !in memoryMessagesSet }


        val processedMessages = memoryMessages
            .filterNot { hasNewSystemMessage && it is SystemMessage }
            .toMutableList()

        processedMessages.addAll(newMessages)

        if (processedMessages.size <= maxMessages) return processedMessages

        // 오래된 메시지 제거
        val messagesToRemove = processedMessages.size - maxMessages
        var removed = 0
        return processedMessages.filter {
            if (it !is SystemMessage && removed < messagesToRemove) {
                removed++
                false
            } else true
        }
    }
}