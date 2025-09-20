package com.example.ai.example

import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatClientTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @Test
    fun test() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val response = chatClient.prompt()
            .user("Spring AI에 대해서 알려줄래?")
            .call()
            .content();

        println(response)
    }
}