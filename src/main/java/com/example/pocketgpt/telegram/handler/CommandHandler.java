package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.telegram.model.TelegramCommand;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.pocketgpt.telegram.model.TelegramInlineButton.*;
import static com.example.pocketgpt.telegram.sender.TelegramBotSender.buildInlineButton;

/**
 * Обработчик команд от пользователя (/start, /info, /help)
 */
@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final TelegramBotSender sender;

    public void handleCommand(TelegramCommand command) {
        switch (command){
            case START -> startCommand();
            default -> sender.sendText("Неизвестная команда...");
        }
    }

    private void startCommand() {
        var keyboard = List.of(
                List.of(
                        buildInlineButton(NEW_CHAT)
                ),
                List.of(
                        buildInlineButton(LIST_CHATS)
                ),
                List.of(
                        buildInlineButton(CHAT_WITHOUT_CONTEXT)
                )
        );

        sender.sendInlineKeyboard(keyboard, "Выберите действие:");
    }

}
