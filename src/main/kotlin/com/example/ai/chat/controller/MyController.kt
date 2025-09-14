package com.example.ai.chat.controller

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MyController(
   private val openAiChatModel: OpenAiChatModel
) {
    private val chatClient: ChatClient = ChatClient.create(openAiChatModel)

    @GetMapping("/ai")
    fun generation(@RequestParam("userInput") userInput: String): String? {

        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
}