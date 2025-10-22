package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
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

    public void handleUserMessage(TelegramUserInfo userInfo) {
        if (isNeedToCreateNewChat()) {
            createAndStartNewChat(userInfo);
            return;
        }





        sender.sendText(userInfo.getChatId(), "Ты отправил сообщение в GPT.");
    }

    private boolean isNeedToCreateNewChat() {
        return false;
    }

    private void createAndStartNewChat(TelegramUserInfo userInfo) {
        sender.sendText(userInfo.getChatId(), "Новый чат создан! Что хочешь обсудить?");
    }

}
