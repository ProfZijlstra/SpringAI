package edu.miu.spring_ai_demo;

import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiDemoApplication.class, args);
	}

	@Bean
	public OpenAiApi chatCompletionApi() {
		return OpenAiApi.builder()
				.baseUrl("http://localhost:11434")
				.apiKey(new NoopApiKey()).build();
	}

	@Bean
	public OpenAiChatModel openAiClient(OpenAiApi openAiApi) {
		return OpenAiChatModel.builder()
				.openAiApi(openAiApi)
				.defaultOptions(OpenAiChatOptions.builder().model("llama3.2").build())
				.build();
	}
}
