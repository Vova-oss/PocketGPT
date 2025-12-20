package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.UserAuthorizationService;
import com.example.pocketgpt.telegram.mapper.TelegramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramHandler {

    private final CallbackHandler callbackHandler;

    private final MsgHandler msgHandler;

    private final CommandHandler commandHandler;

    private final TelegramMapper  telegramMapper;

    private final UserAuthorizationService userAuthorizationService;

    public void handleMessage(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            var userInfo = telegramMapper.toTelegramUserInfo(update);

            userAuthorizationService.authorizationUser(userInfo);

            if (userInfo.getCommand() != null){
                commandHandler.handleCommand(userInfo);
            } else {
                msgHandler.handleUserMessage(userInfo);
            }

        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            callbackHandler.handleCallback(data);
        }
    }

}
