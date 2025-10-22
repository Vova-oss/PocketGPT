package com.example.pocketgpt.telegram.service;

import com.example.pocketgpt.db.domain.UserManager;
import com.example.pocketgpt.telegram.handler.CallbackHandler;
import com.example.pocketgpt.telegram.handler.ChatHandler;
import com.example.pocketgpt.telegram.handler.CommandHandler;
import com.example.pocketgpt.telegram.mapper.TelegramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final CallbackHandler callbackHandler;

    private final ChatHandler chatHandler;

    private final CommandHandler commandHandler;

    private final TelegramMapper  telegramMapper;

    private final UserManager userManager;

    public void handleMessage(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            var userInfo = telegramMapper.toTelegramUserInfo(update);

            userManager.authorizationUser(userInfo);

            if (userInfo.getCommand() != null){
                commandHandler.handleCommand(userInfo);
            } else {
                chatHandler.handleUserMessage(userInfo);
            }

        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            callbackHandler.handleCallback(chatId, data);
        }
    }

}
