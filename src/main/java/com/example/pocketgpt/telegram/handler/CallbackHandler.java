package com.example.pocketgpt.telegram.handler;

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

    public void handleCallback(Long chatId, String data){
        var telegramInlineButton = TelegramInlineButton.fromData(data);
        switch (telegramInlineButton) {
            case DIALOG_WITHOUT_CONTEXT -> sender.sendText(chatId, "Привет! О чём хочешь поговорить?");
            case NEW_DIALOG -> sender.sendText(chatId, "Продолжение диалога");
            case LIST_DIALOGS -> sender.sendText(chatId, "Список диалогов");
            case DELETE_DIALOG -> sender.sendText(chatId, "Удаление диалога");
        }
    }

}
