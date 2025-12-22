package com.example.pocketgpt.telegram.sender;

import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.model.TelegramInlineButton;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class TelegramBotSender extends DefaultAbsSender {

    protected TelegramBotSender(DefaultBotOptions options, @Value("${telegram.bot.token}") String botToken) {
        super(options, botToken);
    }

    public void sendText(String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(TelegramContext.get().getChatId())
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendInlineKeyboard(List<List<InlineKeyboardButton>> keyboard, String commonText) {
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();

        try {
            execute(SendMessage.builder()
                    .chatId(TelegramContext.get().getChatId())
                    .text(commonText)
                    .replyMarkup(markup)
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    public static InlineKeyboardButton buildInlineButton(TelegramInlineButton inlineButton){
        return InlineKeyboardButton.builder()
                .text(inlineButton.getMessage())
                .callbackData(inlineButton.getCallback())
                .build();
    }

    public static InlineKeyboardButton buildInlineButton(String text, String callbackData){
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }
}
