package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Обрабочик команд от пользователя (/start, /info, /help)
 */
@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final TelegramBotSender sender;

    public void handleCommand(TelegramUserInfo userInfo) {
        switch (userInfo.getCommand()){
            case START -> sender.sendMenu();
            default -> sender.sendText("Неизвестная команда...");
        }
    }

}
