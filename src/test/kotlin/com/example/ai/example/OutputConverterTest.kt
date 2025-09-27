package com.example.ai.example

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OutputConverterTest {

    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("OutputConverter를 사용한 예제")
    @Test
    fun outputConverterTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val userInput = "오늘 날씨는 {city} 어때? 그리고 {city}의 내일 날씨는 어때? 답변은 JSON 형식으로 해줘. 예시: {\"today\": \"\", \"tomorrow\": \"\"}"
        val promptTemplate = PromptTemplate.builder()
            .template(userInput)
            .variables(mapOf("city" to "서울"))
            .build().createMessage()
        val prompt = Prompt(promptTemplate)

        val response = chatClient.prompt(prompt)
            .call()
            .content()!!

        println(response)

        val outputConverter = BeanOutputConverter(WeatherInfo::class.java)
        val weatherInfo = outputConverter.convert(response)

        println(weatherInfo)
    }

    data class WeatherInfo(
        val today: String,
        val tomorrow: String
    )

}