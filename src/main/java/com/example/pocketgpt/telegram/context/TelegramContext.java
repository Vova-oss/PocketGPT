package com.example.pocketgpt.telegram.context;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class TelegramContext {

    private static final ThreadLocal<TelegramContext> CONTEXT =
            ThreadLocal.withInitial(TelegramContext::new);

    private Long chatId;

    private Long tgId;

    private String message;

    public static TelegramContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    public static void setContextParams(Update update) {
        var ctx = TelegramContext.get();
        if (update.hasMessage() && update.getMessage().hasText()) {
            ctx.setTgId(update.getMessage().getFrom().getId());
            ctx.setChatId(update.getMessage().getChatId());
            ctx.setMessage(update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            ctx.setTgId(update.getCallbackQuery().getFrom().getId());
            ctx.setChatId(update.getCallbackQuery().getMessage().getChatId());
            ctx.setMessage(update.getCallbackQuery().getData());
        }
    }

}

