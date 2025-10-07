package com.example.ai.example.tool

import com.example.ai.example.tool.dto.DateTimeTools
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ToolCallingTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Tool 어노테이션을 사용한 예제")
    @Test
    fun dateTimeToolsTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("What day is tomorrow?")

        val response = chatClient.prompt(prompt)
            .tools(DateTimeTools())
            .call()
            .content();

        println(response)
    }
}