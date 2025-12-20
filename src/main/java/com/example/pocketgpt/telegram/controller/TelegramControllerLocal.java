package com.example.pocketgpt.telegram.controller;

import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.handler.TelegramHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.example.pocketgpt.telegram.context.TelegramContext.setContextParams;

@Slf4j
@Component
public class TelegramControllerLocal extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    private final TelegramHandler service;

    public TelegramControllerLocal(TelegramHandler service) {
        this.service = service;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            setContextParams(update);
            service.handleMessage(update);
        } finally {
            TelegramContext.clear();
        }
    }

}

