package com.example.ai.common.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.openai.OpenAiChatModel
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatClientConfig {
//    @Bean
//    fun openAiChatModel(): OpenAiChatModel {
//        return OpenAiChatModel(
//            OpenAiChatOptions.builder()
//                .model("gpt-3.5-turbo")
//                .a
//                .withApiKey("sk-...YOUR_KEY...")
//                .build()
//        )
//    }

    @Bean
    fun openAiChatClient(chatModel: OpenAiChatModel): ChatClient {
        return ChatClient.create(chatModel)
    }
}