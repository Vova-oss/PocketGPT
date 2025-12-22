package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.ChatService;
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

    public void handleUserMessage() {
        var createdChat = chatService.saveDraftChatWithNewName();
        if (createdChat.isPresent()) {
            sender.sendText("Новый чат создан! Что хочешь обсудить?");
            return;
        }

        var activeChat = chatService.getActiveChatIfExists();
        if (activeChat.isPresent()) {
            // Запрос в GPT с последними 10 сообщениями
            sender.sendText("Запрос в GPT с последними 10 сообщениями");
            return;
        }





        sender.sendText("Ты отправил сообщение в GPT вне контекста.");
    }

}
