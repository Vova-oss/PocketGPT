package com.example.pocketgpt.telegram.controller;

import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.handler.TelegramHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.pocketgpt.telegram.context.TelegramContext.setContextParams;

@RestController
@RequestMapping("/api/telegram")
@RequiredArgsConstructor
public class TelegramController {

    private final TelegramHandler telegramHandler;

    @PostMapping
    public void onUpdateReceived(@RequestBody Update update) {
        try {
            setContextParams(update);
            telegramHandler.handleMessage(update);
        } finally {
            TelegramContext.clear();
        }
    }

}