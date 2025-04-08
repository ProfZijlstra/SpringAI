package edu.miu.spring_ai_demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ChatController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/ai")
    public String getResponse(
        @RequestParam(defaultValue = "What day is tomorrow?")  String prompt) {
        ChatResponse response = chatClient
                .prompt(prompt)
                .tools(new CalculatorTool())
                .call().chatResponse();

        return response.getResult().getOutput().getText();
    }
}
