package com.example.ai.example.chat

import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatClientStreamTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Prompt 생성자에 String을 넣은 입력")
    @Test
    fun streamPromptTest(): Unit = runBlocking {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val stream = chatClient.prompt(prompt).stream()

        val response = stream.content()

        response
            .asFlow()
            .collect { chunk -> println(chunk) }
    }

}