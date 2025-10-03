package com.example.ai.common.config

import io.micrometer.observation.ObservationRegistry
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.DefaultChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.model.ApiKey
import org.springframework.ai.model.tool.DefaultToolExecutionEligibilityPredicate
import org.springframework.ai.model.tool.ToolCallingManager
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.ai.openai.api.OpenAiApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.support.RetryTemplate
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.DefaultResponseErrorHandler
import org.springframework.web.client.RestClient
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class ChatClientConfig(
    private val chatClientBuilder: ChatClient.Builder
) {
    @Bean
    fun openAiChatClient(): ChatClient {
        return chatClientBuilder.build()
    }

//    @Value("\${OPENAI_API_KEY}")
//    lateinit var openAiKey: String
//    @Bean
//    fun openAiApi(): OpenAiApi {
//        val apiKey = ApiKey({ openAiKey })
//
//        return OpenAiApi(
//            "https://api.openai.com/v1",
//            apiKey,
//            LinkedMultiValueMap(),
//            "/chat/completions",
//            "/embeddings",
//            RestClient.builder(),
//            WebClient.builder(),
//            DefaultResponseErrorHandler()
//        )
//    }
//
//    @Bean
//    fun openAiChatOptions(): OpenAiChatOptions {
//        return OpenAiChatOptions.builder()
//            .model("gpt-3.5-turbo")
//            .temperature(0.7)
//            .maxTokens(1024)
//            .build()
//    }
//
//
//    @Bean
//    fun observationRegistry(): ObservationRegistry {
//        return ObservationRegistry.create()
//    }
//
//    @Bean
//    fun toolExecutionEligibilityPredicate(): ToolExecutionEligibilityPredicate {
//        return DefaultToolExecutionEligibilityPredicate()
//    }
//
//    @Bean
//    fun chatModel(
//        openAiApi: OpenAiApi,
//        openAiChatOptions: OpenAiChatOptions,
//        toolCallingManager: ToolCallingManager,
//        retryTemplate: RetryTemplate,
//        observationRegistry: ObservationRegistry,
//        toolExecutionEligibilityPredicate: ToolExecutionEligibilityPredicate
//    ): ChatModel {
//        return OpenAiChatModel(
//            openAiApi,
//            openAiChatOptions,
//            toolCallingManager,
//            retryTemplate,
//            observationRegistry,
//            toolExecutionEligibilityPredicate
//        )
//    }
}