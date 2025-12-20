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

import static com.example.pocketgpt.telegram.model.TelegramInlineButton.*;

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

    public void sendMenu() {
        InlineKeyboardMarkup markup = InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(
                                buildInlineButton(NEW_DIALOG)
                        ),
                        List.of(
                                buildInlineButton(LIST_DIALOGS)
                        ),
                        List.of(
                                buildInlineButton(DIALOG_WITHOUT_CONTEXT)
                        )
                ))
                .build();

        try {
            execute(SendMessage.builder()
                    .chatId(TelegramContext.get().getChatId())
                    .text("Выберите действие:")
                    .replyMarkup(markup)
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private InlineKeyboardButton buildInlineButton(TelegramInlineButton inlineButton){
        return InlineKeyboardButton.builder()
                .text(inlineButton.getMessage())
                .callbackData(inlineButton.getUrl())
                .build();
    }
}
