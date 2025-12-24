package com.example.pocketgpt.gpt.controller;


import com.example.pocketgpt.gpt.service.OpenAiFacade;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OpenAiFacade openAiFacade;

    public ChatController(OpenAiFacade openAiFacade) {
        this.openAiFacade = openAiFacade;
    }

    @PostMapping
    public Map<String, Object> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        String reply = openAiFacade.askGpt(userMessage);
        return Map.of("reply", reply);
    }
}
