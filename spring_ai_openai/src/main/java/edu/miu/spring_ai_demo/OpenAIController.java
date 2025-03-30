package edu.miu.spring_ai_demo;

import java.util.Map;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class OpenAIController {

    @Autowired
    private OpenAiChatModel chatModel;

    @GetMapping("/ai")
    public String getResponse(String subject) {
        String template = "Tell me a joke about {subject}";
        PromptTemplate promptTemplate = new PromptTemplate(template,
                Map.of("subject", subject));
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        ChatResponse response = chatModel.call(prompt);
        return response.getResult().getOutput().getText();
    }

}
