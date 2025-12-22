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
            case NEW_CHAT -> newDialog();
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

    private void newDialog() {
        chatService.createNewChat();
        sender.sendText("Придумай название диалога");
    }

    private void listOfChats() {
        var chats = chatService.findAllDialogsByUser();
        var inlineChats = chats.stream()
                .map(chat -> List.of(
                        buildInlineButton(chat.getName(), WORK_WITH_CHAT.getCallback() + chat.getId() + ":" + chat.getName())
                ))
                .toList();

        sender.sendInlineKeyboard(inlineChats, "Выберите нужный чат:");
    }

    private void workWithChat() {
        var userMessage = TelegramContext.get().getMessage().split(":");
        var chatId = userMessage[1];
        var chatName = userMessage[2];
        
        var inlineKeyboard = List.of(
                List.of(
                        buildInlineButton( 
                                CONTINUE_CHAT.getMessage(),
                                CONTINUE_CHAT.getCallback() + chatId + ":" + chatName
                        )
                ),
                List.of(
                        buildInlineButton(
                                DELETE_CHAT.getMessage(),
                                DELETE_CHAT.getCallback() + chatId + ":" + chatName
                        )
                )               
        );
        
        sender.sendInlineKeyboard(inlineKeyboard, "Выберите действие:");
    }

    private void continueChat() {
        var userMessage = TelegramContext.get().getMessage().split(":");
        var chatId = userMessage[1];
        var chatName = userMessage[2];
        chatService.activateChatById(chatId);
        sender.sendText(String.format("Вы продолжаете чат \"%s\"", chatName));
    }
    
    private void clarificationAboutDeletingChat(){
        var userMessage = TelegramContext.get().getMessage().split(":");
        var chatId = userMessage[1];
        var chatName = userMessage[2];

        var inlineKeyboard = List.of(
                List.of(
                        buildInlineButton(
                                YES_DELETE_CHAT.getMessage(),
                                YES_DELETE_CHAT.getCallback() + chatId + ":" + chatName
                        )
                ),
                List.of(
                        buildInlineButton(
                                NO_DONT_DELETE_CHAT.getMessage(),
                                NO_DONT_DELETE_CHAT.getCallback() + chatId + ":" + chatName
                        )
                )
        );

        sender.sendInlineKeyboard(inlineKeyboard, String.format("Вы уверены, что хотите удалить чат \"%s\"?", chatName));
    }

    private void permanentlyDeletingChat() {
        var userMessage = TelegramContext.get().getMessage().split(":");
        var chatId = userMessage[1];
        var chatName = userMessage[2];

        chatService.deleteChatById(chatId);
        sender.sendText(String.format("Чат \"%s\" был удалён", chatName));
    }


}
