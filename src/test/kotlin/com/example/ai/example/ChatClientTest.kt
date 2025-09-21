package com.example.ai.example

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatClientTest {
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

    @DisplayName("Prompt 생성자에 Message 넣은 입력")
    @Test
    fun messagePromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val userMessage: Message = UserMessage("Spring AI에 대해서 알려줄래?")
        val systemMessage: Message = SystemMessage("당신은 Java 및 Spring에 대한 전문가입니다. 사용자가 물어볼 때, 다른 프로젝트와 어떻게 다른지 비교하여 분석해주면 더 좋을 것 같습니다. ")
        val prompt = Prompt(listOf(userMessage, systemMessage))

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }

    @DisplayName("Prompt 생성자에 ChatOptions를 넣은 입력")
    @Test
    fun chatOptionsPromptTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val userMessage: Message = UserMessage("Spring AI에 대해서 알려줄래?")
        val systemMessage: Message = SystemMessage("당신은 Java 및 Spring에 대한 전문가입니다. 사용자가 물어볼 때, 다른 프로젝트와 어떻게 다른지 비교하여 분석해주면 더 좋을 것 같습니다. ")

        val chatOption = OpenAiChatOptions.builder()
            .model("gpt-3.5-turbo")
            .temperature(0.7)
            .maxTokens(1024)
            .build()

        val prompt = Prompt(listOf(userMessage, systemMessage), chatOption)

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }
}