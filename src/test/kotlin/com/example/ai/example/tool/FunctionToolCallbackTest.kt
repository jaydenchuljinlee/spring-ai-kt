package com.example.ai.example.tool

import com.example.ai.example.tool.dto.DateTimeTools
import com.example.ai.tool.dto.FunctionWeather
import com.example.ai.tool.dto.WeatherRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.tool.definition.ToolDefinition
import org.springframework.ai.tool.function.FunctionToolCallback
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FunctionToolCallbackTest {

    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Function Tool Callback 예제")
    @Test
    fun functionToolCallbackTest() {
        val functionToolCallback = FunctionToolCallback.builder("currentWeather", FunctionWeather())
            .description("Get the weather in location")
            .inputType(WeatherRequest::class.java)
            .build()

        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("What's the weather like in Copenhagen?")

        val response = chatClient.prompt(prompt)
            .toolCallbacks(functionToolCallback)
            .call()
            .content();

        println(response)
    }


}