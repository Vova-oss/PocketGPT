package com.example.pocketgpt.telegram.config;

import com.example.pocketgpt.telegram.controller.TelegramControllerLocal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramControllerLocal telegramBotController) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(telegramBotController);
        return botsApi;
    }

    @Bean
    public DefaultBotOptions defaultBotOptions(){
        return new DefaultBotOptions();
    }

}
