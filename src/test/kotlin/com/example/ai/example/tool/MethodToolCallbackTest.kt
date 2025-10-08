package com.example.ai.example.tool

import com.example.ai.example.tool.dto.DateTimeTools
import org.apache.el.util.ReflectionUtil
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.tool.method.MethodToolCallback
import org.springframework.ai.tool.support.ToolDefinitions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.ReflectionUtils

@SpringBootTest
class MethodToolCallbackTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Method Tool Callback 예제")
    @Test
    fun methodToolCallbackTest() {
        val method = ReflectionUtils.findMethod(DateTimeTools::class.java, "getCurrentDateTime")!!
        val methodToolCallback = MethodToolCallback.builder()
            .toolDefinition(ToolDefinitions.builder(method)
                .description("Get the current date and time in the user's timezone")
                .build())
            .toolMethod(method)
            .toolObject(DateTimeTools())
            .build()

        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("What day is tomorrow?")

        val response = chatClient.prompt(prompt)
            .toolCallbacks(methodToolCallback)
            .call()
            .content();

        println(response)

    }
}