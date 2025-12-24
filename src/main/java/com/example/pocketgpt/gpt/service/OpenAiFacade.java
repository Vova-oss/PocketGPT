package com.example.pocketgpt.gpt.service;

import com.example.pocketgpt.db.entity.Message;
import com.example.pocketgpt.db.entity.enums.Role;
import com.example.pocketgpt.gpt.model.GptMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiFacade {

    @Value("${openai.enabled}")
    private boolean openaiEnabled;

    private final OpenAiService openAiService;

    public String askGpt(String message) {
        if(!openaiEnabled)
            return "Заглушка при запросе к GPT";

        var gptMessage = GptMessage.builder()
                .role(Role.USER.getValue())
                .content(message)
                .build();

        return openAiService.askGpt(List.of(gptMessage));
    }


    public String askGpt(List<Message> messages) {
        if(!openaiEnabled)
                return "Заглушка при запросе к GPT";

        var gptMessages = messages.stream()
                .sorted(Comparator.comparing(Message::getId))
                .map(message -> GptMessage.builder()
                        .role(message.getRole().getValue())
                        .content(message.getMessageText())
                        .build()
                )
                .toList();

        return openAiService.askGpt(gptMessages);
    }
}
