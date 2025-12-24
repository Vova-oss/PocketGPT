package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.ChatService;
import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.model.TelegramInlineButton;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.pocketgpt.telegram.model.TelegramInlineButton.*;
import static com.example.pocketgpt.telegram.sender.TelegramBotSender.buildInlineButton;

/**
 * Обработчик нажатия на inline-кнопки (прямо под сообщением - НЕ контекстное меню)
 */
@Service
@RequiredArgsConstructor
public class CallbackHandler {

    private final TelegramBotSender sender;

    private final ChatService chatService;

    public void handleCallback(String data) {
        var telegramInlineButton = TelegramInlineButton.fromData(data);

        switch (telegramInlineButton) {
            case CHAT_WITHOUT_CONTEXT -> chatWithoutContext();
            case NEW_CHAT -> newChat();
            case LIST_CHATS -> listOfChats();
            case WORK_WITH_CHAT -> workWithChat();
            case CONTINUE_CHAT -> continueChat();
            case DELETE_CHAT -> clarificationAboutDeletingChat();
            case YES_DELETE_CHAT -> permanentlyDeletingChat();
            case NO_DONT_DELETE_CHAT -> sender.sendText("Чат оставлен");
        }
    }

    private void chatWithoutContext() {
        chatService.deactivateActiveChatsByUserId();
        sender.sendText("Привет! О чём хочешь поговорить?");
    }

    private void newChat() {
        var tgResponse = chatService.createNewChat();
        sender.sendText(tgResponse.getReply());
    }

    private void listOfChats() {
        var chats = chatService.findNotDraftChatsByUser();
        var inlineChats = chats.stream()
                .map(chat -> List.of(
                        buildInlineButton(chat.getName(), WORK_WITH_CHAT.getCallback() + chat.getId())
                ))
                .toList();

        sender.sendInlineKeyboard(inlineChats, "Выберите нужный чат:");
    }

    private void workWithChat() {
        var userMessage = TelegramContext.get().getMessage().split(":");
        var chatId = userMessage[1];
        
        var inlineKeyboard = List.of(
                List.of(
                        buildInlineButton( 
                                CONTINUE_CHAT.getMessage(),
                                CONTINUE_CHAT.getCallback() + chatId
                        )
                ),
                List.of(
                        buildInlineButton(
                                DELETE_CHAT.getMessage(),
                                DELETE_CHAT.getCallback() + chatId
                        )
                )               
        );
        
        sender.sendInlineKeyboard(inlineKeyboard, "Выберите действие:");
    }

    private void continueChat() {
        var context = TelegramContext.get();
        var userMessage = context.getMessage().split(":");
        var chatId = userMessage[1];
        chatService.activateChatById(chatId);

        sender.sendText("Вы продолжаете чат");
    }
    
    private void clarificationAboutDeletingChat(){
        var context = TelegramContext.get();
        var userMessage = context.getMessage().split(":");
        var chatId = userMessage[1];

        var inlineKeyboard = List.of(
                List.of(
                        buildInlineButton(
                                YES_DELETE_CHAT.getMessage(),
                                YES_DELETE_CHAT.getCallback() + chatId
                        )
                ),
                List.of(
                        buildInlineButton(
                                NO_DONT_DELETE_CHAT.getMessage(),
                                NO_DONT_DELETE_CHAT.getCallback() + chatId
                        )
                )
        );

        sender.sendInlineKeyboard(inlineKeyboard, "Вы уверены, что хотите удалить этот чат?");
    }

    private void permanentlyDeletingChat() {
        var context = TelegramContext.get();
        var userMessage = context.getMessage().split(":");
        var chatId = userMessage[1];

        chatService.deleteChatById(chatId);
        sender.sendText("Чат был удалён");
    }


}
