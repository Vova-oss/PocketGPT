package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.telegram.model.TelegramCommand;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Обработчик команд от пользователя (/start, /info, /help)
 */
@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final TelegramBotSender sender;

    public void handleCommand(TelegramCommand command) {
        switch (command){
            case START -> sender.sendMenu();
            default -> sender.sendText("Неизвестная команда...");
        }
    }

}
