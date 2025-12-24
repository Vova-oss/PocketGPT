package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.entity.Message;
import com.example.pocketgpt.db.entity.enums.Role;
import com.example.pocketgpt.db.service.ChatService;
import com.example.pocketgpt.db.service.MessageService;
import com.example.pocketgpt.gpt.service.OpenAiFacade;
import com.example.pocketgpt.telegram.context.TelegramContext;
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

    private final MessageService messageService;

    private final OpenAiFacade openAiFacade;

    public void handleUserMessage() {
        var createdChat = chatService.saveDraftChatWithNewName();
        if (createdChat.isPresent()) {
            sender.sendText("Новый чат создан! Что хочешь обсудить?");
            return;
        }

        var userMessage = TelegramContext.get().getMessage();

        var activeChat = chatService.getActiveChatIfExists();
        if (activeChat.isPresent()) {
            var chat = activeChat.get();
            var messages = messageService.get10LastMessagesByChatId(chat.getId());
            messages.add(createCurrentUserMessage(userMessage));
            var gptResponse = openAiFacade.askGpt(messages);
            messageService.saveUserMessageAndGptResponse(gptResponse, userMessage, chat);
            sender.sendText(gptResponse);
            return;
        }

        var gptResponse = openAiFacade.askGpt(userMessage);
        sender.sendText(gptResponse);
    }

    private Message createCurrentUserMessage(String userMessage) {
        var message = new Message();
        message.setMessageText(userMessage);
        message.setRole(Role.USER);
        return message;
    }

}
