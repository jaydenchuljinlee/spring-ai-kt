package com.example.ai.memory.controller

import com.example.ai.memory.service.ChatMemoryService
import org.springframework.ai.chat.messages.Message
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/memory/in-memory")
@RestController
class InMemoryController(
    private val inMemoryMemoryService: ChatMemoryService
) {
    @PostMapping()
    fun sendMessage(userMessage: String): String {
        return inMemoryMemoryService.createConversation(userMessage)
    }

    @PostMapping("/{conversationId}")
    fun sendMessageWithConversation(
        @PathVariable("conversationId") conversationId: String,
        userMessage: String): Message {
        return inMemoryMemoryService.sendMessage(conversationId, userMessage)
    }
}