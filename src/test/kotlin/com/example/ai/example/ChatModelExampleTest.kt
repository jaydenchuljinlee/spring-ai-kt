package com.example.ai.example

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.model.StreamingChatModel
import org.springframework.ai.chat.prompt.ChatOptions
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.evaluation.EvaluationRequest
import org.springframework.ai.evaluation.EvaluationResponse
import org.springframework.ai.evaluation.Evaluator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux

@SpringBootTest
class ChatModelExampleTest {
    @Autowired
    lateinit var chatModel: ChatModel

    @Autowired(required = false)
    lateinit var streamingChatModel: StreamingChatModel

    @Autowired(required = false)
    lateinit var evaluator: Evaluator

    @Test
    @DisplayName("기본적인 시스템 및 사용자 메시지를 사용한 ChatModel 호출")
    fun simple_prompt_call_with_user_and_system_message() {
        val prompt = Prompt(
            listOf(
                SystemMessage("You are a helpful assistant."),
                UserMessage("What is the capital of France?")
            )
        )

        val response = chatModel.call(prompt)
        println("AI 응답: ${response.result.output.text}")
    }

    @Test
    @DisplayName("temperature 옵션을 활용한 ChatModel 호출")
    fun call_with_temperature_option() {
        val prompt = Prompt(
            listOf(UserMessage("Tell me a joke about cats.")),
            ChatOptions.builder().temperature(0.9).build()
        )

        val response = chatModel.call(prompt)
        println("고양이 농담: ${response.result.output.text}")
    }

    @Test
    @DisplayName("StreamingChatModel을 활용한 스트리밍 응답 출력")
    fun streaming_response_test() {
        if (!::streamingChatModel.isInitialized) return

        val prompt = Prompt(listOf(UserMessage("Write a short story in two sentences.")))
        val stream: Flux<ChatResponse> = streamingChatModel.stream(prompt)

        println("Streaming 응답:")
        stream.toStream().forEach { res ->
            print(res.result.output.text)
        }
        println()
    }

    @Test
    @DisplayName("Evaluator를 이용한 ChatModel 응답 평가")
    fun evaluate_response_with_criteria() {
        if (!::evaluator.isInitialized) return

        val prompt = Prompt(listOf(UserMessage("Explain quantum computing in simple terms.")))
        val actualResponse = chatModel.call(prompt)
        val evalRequest = EvaluationRequest(
            prompt.contents,  // userText
            actualResponse.result.output.text  // responseContent
        )

        val result: EvaluationResponse = evaluator.evaluate(evalRequest)
        // 결과 확인
        println("합격 여부: ${result.isPass}")
        println("점수: ${result.score}")
        println("피드백: ${result.feedback}")
        println("메타데이터: ${result.metadata}")
    }
}