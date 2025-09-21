package com.example.ai.example

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference

@SpringBootTest
class ChatClientCallTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("Prompt 생성자에 String을 넣은 입력")
    @Test
    fun stringPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }

    @DisplayName("Prompt ChatResponse 타입 사용")
    @Test
    fun chatResponsePromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val response = chatClient.prompt(prompt)
            .call()
            .chatResponse();

        println(response)
    }

    @DisplayName("Prompt Entity 사용")
    @Test
    fun entityPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val response = chatClient.prompt(prompt)
            .call()
            .entity(Map::class.java)

        println(response?.values)
    }

    @DisplayName("Prompt ResponseEntity Class<T> 사용")
    @Test
    fun responseEntityPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대해서 알려줄래?")

        val response = chatClient.prompt(prompt)
            .call()
            .responseEntity(Map::class.java)

        println(response)
    }

    @DisplayName("Prompt ResponseEntity ParameterizedTypeReference 사용")
    @Test
    fun parameterizedTypeResponseEntityPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대한 면접 질문을 질문과 응답 형태로 만들어줄래? ")

        val typeRef = object : ParameterizedTypeReference<Map<String, Any>>() {}

        val response = chatClient.prompt(prompt)
            .call()
            .responseEntity(typeRef)

        println(response)
    }

    @DisplayName("Prompt ResponseEntity StructuredOutputConverter 사용")
    @Test
    fun structuredTypeResponseEntityPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val prompt = Prompt("Spring AI에 대한 면접 질문을 질문과 응답 형태로 만들어줄래? ")
        val typeRef = object : ParameterizedTypeReference<Map<String, Any>>() {}
        val converter = BeanOutputConverter(typeRef)

        val response = chatClient.prompt(prompt)
            .call()
            .responseEntity(converter)

        println(response)
    }
}