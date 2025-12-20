package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.ChatService;
import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Обработчик обычного сообщения пользователя
 */
@Service
@RequiredArgsConstructor
public class MsgHandler {

    private final TelegramBotSender sender;

    private final ChatService chatService;

    public void handleUserMessage(TelegramUserInfo userInfo) {
        var tgId = TelegramContext.get().getTgId();

        var createdChat = chatService.saveDraftChatWithNewName(userInfo, tgId);
        if (createdChat.isPresent()){
            sender.sendText("Новый чат создан! Что хочешь обсудить?");
            return;
        }


        sender.sendText("Ты отправил сообщение в GPT.");
    }



}
