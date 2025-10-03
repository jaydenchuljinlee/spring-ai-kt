package com.example.ai.chat.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.messages.Message
import org.springframework.stereotype.Service

@Service
class OpenAiChatClientService(
    private val openAiChatClient: ChatClient
): ChatClientService {
    override fun sendMessage(userMessage: String): Message {
        val message = openAiChatClient.prompt()
            .user(userMessage)
            .call()
            .chatResponse()!!.result.output
        return message
    }

    override fun sendMessageWithConversation(userMessage: String, conversationMessages: List<Message>): Message {
        TODO("Not yet implemented")
    }
}