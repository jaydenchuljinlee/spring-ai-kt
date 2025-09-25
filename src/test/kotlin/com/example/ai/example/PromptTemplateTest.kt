package com.example.ai.example

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.template.st.StTemplateRenderer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PromptTemplateTest {
    @Autowired
    lateinit var chatClientBuilder: ChatClient.Builder

    @DisplayName("PromptTemplate render() 메서드를 사용한 예제")
    @Test
    fun promptTemplateRenderTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val promptTemplate = PromptTemplate.builder()
            .renderer(StTemplateRenderer.builder()
                .startDelimiterToken('<')
                .endDelimiterToken('>').build()
            )
            .template("""
                Spring AI의 <chapter> 대해서 알려줄래?
            """.trimIndent())
            .build()

        val prompt = promptTemplate.render(mapOf("chapter" to "chatClient API"))

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }

    @DisplayName("PromptTemplate create() 메서드를 사용한 예제")
    @Test
    fun promptTemplateCreateTest() {
        val chatClient: ChatClient = chatClientBuilder.build()

        val promptTemplate = PromptTemplate("Spring AI에 대해서 알려줄래?")
        val prompt = promptTemplate.create()

        val response = chatClient.prompt(prompt)
            .call()
            .content();

        println(response)
    }
}