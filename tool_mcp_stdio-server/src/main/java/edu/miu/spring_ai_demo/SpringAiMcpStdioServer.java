package edu.miu.spring_ai_demo;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiMcpStdioServer {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiMcpStdioServer.class, args);
	}

	@Bean
	public List<ToolCallback> tools(DateTimeTool dateTimeTool, CalculatorTool calculatorTool) {
		return List.of(ToolCallbacks.from(dateTimeTool, calculatorTool));
	}
}

