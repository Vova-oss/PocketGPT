package com.example.pocketgpt.telegram.service;

import com.example.pocketgpt.telegram.handler.CallbackHandler;
import com.example.pocketgpt.telegram.handler.ChatHandler;
import com.example.pocketgpt.telegram.handler.CommandHandler;
import com.example.pocketgpt.telegram.model.TelegramCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final CallbackHandler callbackHandler;

    private final ChatHandler chatHandler;

    private final CommandHandler commandHandler;

    public void handleMessage(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            var command = TelegramCommand.fromText(text);

            if (command != null){
                commandHandler.handleCommand(chatId, command);
            } else {
                chatHandler.handleUserMessage(chatId, text);
            }

        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            callbackHandler.handleCallback(chatId, data);
        }
    }

}
