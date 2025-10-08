package com.example.ai.example.tool

import com.example.ai.example.tool.dto.DateTimeTools
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.support.ToolCallbacks
import org.springframework.ai.tool.ToolCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ToolCallingTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Tool 어노테이션을 사용한 Retrieval 예제")
    @Test
    fun retrievalToolsTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("What day is tomorrow?")

        val response = chatClient.prompt(prompt)
            .tools(DateTimeTools())
            .call()
            .content();

        println(response)
    }

    @DisplayName("Tool 어노테이션을 사용한 Take Action 예제")
    @Test
    fun takeActionToolsTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Can you set an alarm 10 minutes from now?")

        val response = chatClient.prompt(prompt)
            .tools(DateTimeTools())
            .call()
            .content();

        println(response)
    }

    @DisplayName("ChatClient.builder() 옵션에 defaultCallbacks를 사용한 예제")
    @Test
    fun defaultCallbackTest() {
        val toolCallback = ToolCallbacks.from(DateTimeTools())
        val chatClient = chatClientBuilder
            .defaultToolCallbacks(toolCallback.toList())
            .build()

        val prompt = Prompt("What day is tomorrow?")

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }
}