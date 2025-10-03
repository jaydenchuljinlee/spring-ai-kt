package com.example.ai.memory.controller

import com.example.ai.memory.service.ChatMemoryService
import org.springframework.ai.chat.messages.Message
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/memory")
@RestController
class ChatMemoryController(
    private val chatMemoryService: ChatMemoryService
) {
    @PostMapping("/in-memory")
    fun sendMessage(userMessage: String): String {
        return chatMemoryService.createConversation(userMessage)
    }

    @PostMapping("/in-memory/{conversationId}")
    fun sendMessageWithConversation(
        @PathVariable("conversationId") conversationId: String,
        userMessage: String): Message {
        return chatMemoryService.sendMessage(conversationId, userMessage)
    }
}