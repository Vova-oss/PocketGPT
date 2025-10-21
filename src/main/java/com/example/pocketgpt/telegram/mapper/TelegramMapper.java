package com.example.pocketgpt.telegram.mapper;

import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import com.example.pocketgpt.telegram.model.TelegramCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramMapper {

    public TelegramUserInfo toTelegramUserInfo(Update update) {
        var message = update.getMessage();
        var from = message.getFrom();
        var messageText = message.getText();

        return TelegramUserInfo.builder()
                .telegramId(from.getId())
                .firstName(from.getFirstName())
                .lastName(from.getLastName())
                .username(from.getUserName())
                .messageText(messageText)
                .command(TelegramCommand.fromMessageText(messageText))
                .chatId(message.getChatId())
                .build();
    }

}
