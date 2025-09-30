package com.example.ai.common.advisor

import org.springframework.ai.chat.client.ChatClientMessageAggregator
import org.springframework.ai.chat.client.ChatClientRequest
import org.springframework.ai.chat.client.ChatClientResponse
import org.springframework.ai.chat.client.advisor.api.CallAdvisor
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain
import reactor.core.publisher.Flux


class MyAdvisor: CallAdvisor, StreamAdvisor {
    override fun getOrder(): Int {
        return 1
    }

    override fun getName(): String {
        return "my advisor"
    }

    override fun adviseStream(
        chatClientRequest: ChatClientRequest,
        streamAdvisorChain: StreamAdvisorChain
    ): Flux<ChatClientResponse> {
        val chatResponse = streamAdvisorChain.nextStream(chatClientRequest)
        return (ChatClientMessageAggregator()).aggregateChatClientResponse(
            chatResponse
        ) { println("advise stream") }
    }

    override fun adviseCall(
        chatClientRequest: ChatClientRequest,
        callAdvisorChain: CallAdvisorChain
    ): ChatClientResponse {
        println("advise call")
        return callAdvisorChain.nextCall(chatClientRequest)
    }
}