package com.example.pocketgpt.telegram.handler;

import com.example.pocketgpt.db.service.UserAuthorizationService;
import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.model.TelegramCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class TelegramHandler {

    private final CallbackHandler callbackHandler;

    private final MsgHandler msgHandler;

    private final CommandHandler commandHandler;

    private final UserAuthorizationService userAuthorizationService;

    public void handleMessage(Update update){
        if (update.hasMessage() && update.getMessage().hasText()) {
            userAuthorizationService.authorizationUser(update);

            var message = TelegramContext.get().getMessage();
            var command = TelegramCommand.fromMessageText(message);
            if (command != null){
                commandHandler.handleCommand(command);
            } else {
                msgHandler.handleUserMessage();
            }

        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            callbackHandler.handleCallback(data);
        }
    }

}
