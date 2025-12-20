package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.ChatService;
import com.example.pocketgpt.telegram.model.TelegramInlineButton;
import com.example.pocketgpt.telegram.sender.TelegramBotSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
            case DIALOG_WITHOUT_CONTEXT -> dialogWithoutContext();
            case NEW_DIALOG -> newDialog();
            case LIST_DIALOGS -> sender.sendText("Список диалогов");
            case DELETE_DIALOG -> sender.sendText("Удаление диалога");
        }
    }

    private void dialogWithoutContext() {
        chatService.deactivateActiveChatsByUserId();
        sender.sendText("Привет! О чём хочешь поговорить?");
    }

    private void newDialog() {
        chatService.createNewChat();
        sender.sendText("Придумай название диалога");
    }

}
