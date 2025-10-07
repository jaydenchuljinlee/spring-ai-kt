package com.example.ai.example.advisor

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AdvisorTest {

    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Custom Advisor를 사용한 예제")
    @Test
    fun loggerAdvisorTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }
}