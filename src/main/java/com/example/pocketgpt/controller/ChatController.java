package com.example.pocketgpt.controller;


import com.example.pocketgpt.service.OpenAiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OpenAiService openAiService;

    public ChatController(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping
    public Map<String, Object> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        String reply = openAiService.askGpt(userMessage);
        return Map.of("reply", reply);
    }
}
