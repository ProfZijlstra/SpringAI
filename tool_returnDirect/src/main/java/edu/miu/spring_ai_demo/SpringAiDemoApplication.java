package edu.miu.spring_ai_demo;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiDemoApplication.class, args);
	}

	@Bean
    public ChatClient chatClient(ChatModel chatModel) {
		ChatClient.Builder builder = ChatClient.builder(chatModel)
			.defaultTools(new DateTimeTools());
		return builder.build();
    }
}

