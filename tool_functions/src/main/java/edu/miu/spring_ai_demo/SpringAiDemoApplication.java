package edu.miu.spring_ai_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
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
		ToolCallback additionTool = FunctionToolCallback
			.builder("additionTool", new AdditionTool())
			.description("Add two numbers together")
			.inputType(AdditionRequest.class)
			.build();

		ChatClient.Builder builder = ChatClient.builder(chatModel);
		builder.defaultTools(additionTool);
		return builder.build(); 
    }
}
