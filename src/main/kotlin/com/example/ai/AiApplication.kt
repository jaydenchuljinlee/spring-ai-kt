package com.example.ai

import org.springframework.ai.model.openai.autoconfigure.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
	exclude = [
		OpenAiChatAutoConfiguration::class,
		OpenAiAudioSpeechAutoConfiguration::class,
		OpenAiEmbeddingAutoConfiguration::class,
		OpenAiAudioTranscriptionAutoConfiguration::class,
		OpenAiImageAutoConfiguration::class,
		OpenAiModerationAutoConfiguration::class
	]
)
class AiApplication

fun main(args: Array<String>) {
	runApplication<AiApplication>(*args)
}
