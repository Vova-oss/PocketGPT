package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Обработчик обычного сообщения пользователя
 */
@Service
@RequiredArgsConstructor
public class ChatHandler {

    private final TelegramBotSender sender;

    public void handleUserMessage(Long chatId, String text) {
        sender.sendText(chatId, "Ты отправил сообщение в GPT.");
    }

}
