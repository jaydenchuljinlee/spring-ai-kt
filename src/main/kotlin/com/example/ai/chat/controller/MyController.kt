package com.example.ai.chat.controller

import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(
    chatClientBuilder: ChatClient.Builder
) {
    private val chatClient: ChatClient = chatClientBuilder.build()

    fun generation(userInput: String): String? {
        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
}