package edu.miu.spring_ai_demo;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiDemoApplication.class, args);
	}

	@Bean
    public ChatClient chatClient(ChatModel chatModel, VectorStore vectorStore) {
		Advisor memory = new MessageChatMemoryAdvisor(new InMemoryChatMemory());

		// Advisor retrieval = new QuestionAnswerAdvisor(vectorStore);
		Advisor retrieval = RetrievalAugmentationAdvisor.builder()
		.documentRetriever(VectorStoreDocumentRetriever.builder()
				.vectorStore(vectorStore)
				.similarityThreshold(0.50)
				.topK(5)
				.build())
		.build();

		ChatClient.Builder builder = ChatClient.builder(chatModel);
		builder.defaultAdvisors(List.of(retrieval, memory));
		return builder.build();
    }
}

