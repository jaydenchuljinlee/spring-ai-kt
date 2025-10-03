package com.example.ai.chat.service

import org.springframework.ai.chat.messages.Message

interface ChatClientService {
    fun sendMessage(userMessage: String): Message
    fun sendMessageWithConversation(userMessage: String, conversationMessages: List<Message>): Message
}