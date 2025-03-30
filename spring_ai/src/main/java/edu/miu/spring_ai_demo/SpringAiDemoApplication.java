package edu.miu.spring_ai_demo;

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
    ChatClient chatClient(ChatModel chatModel) {
		ChatClient.Builder builder = ChatClient.builder(chatModel);
		return builder.build(); // no customization at this point in time
    }
}


// return builder.defaultSystem("You are a military recruit and should begin and end every answer with 'Sir'")
// .build();
