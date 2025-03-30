package edu.miu.spring_ai_demo;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class OllamaController {
    @Autowired
    private OllamaChatModel chatModel;

    @GetMapping("/ai")    
    public String getResponse(String message) {
        ChatResponse response = chatModel.call(new Prompt("Tell me a joke"));
        return response.getResult().getOutput().getText();
    }
}
