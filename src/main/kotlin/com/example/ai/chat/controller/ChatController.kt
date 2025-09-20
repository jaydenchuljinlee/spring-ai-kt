package com.example.ai.chat.controller

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    chatClientBuilder: ChatClient.Builder
) {
    private val chatClient: ChatClient = chatClientBuilder.build()

    @GetMapping("/chat")
    fun generation(@RequestParam("userInput") userInput: String): String? {
        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
}