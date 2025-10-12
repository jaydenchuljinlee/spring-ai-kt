package com.example.ai.memory.controller

import com.example.ai.memory.service.JdbcChatMemoryService
import org.springframework.ai.chat.messages.Message
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/memory/jdbc")
@RestController
class JdbcChatMemoryController(
    private val jdbcChatMemoryService: JdbcChatMemoryService
) {
    @PostMapping()
    fun sendMessage(userMessage: String): String {
        return jdbcChatMemoryService.createConversation(userMessage)
    }

    @PostMapping("/{conversationId}")
    fun sendMessageWithConversation(
        @PathVariable("conversationId") conversationId: String,
        userMessage: String): Message {
        return jdbcChatMemoryService.sendMessage(conversationId, userMessage)
    }
}