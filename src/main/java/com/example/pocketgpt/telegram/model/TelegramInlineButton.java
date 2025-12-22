package com.example.pocketgpt.telegram.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum TelegramInlineButton {

    CHAT_WITHOUT_CONTEXT("chat_without_context", "Чат без контекста"),

    LIST_CHATS("list_chats", "Мои чаты"),

    NEW_CHAT("new_chat", "Новый чат"),

    WORK_WITH_CHAT("work_with_chat:", StringUtils.EMPTY),

    CONTINUE_CHAT("continue_chat:", "Продолжить чат"),

    DELETE_CHAT("delete_chat:", "Удалить чат"),

    YES_DELETE_CHAT("yes_delete_chat:", "Да, удалить этот чат"),

    NO_DONT_DELETE_CHAT("no_dont_delete_chat", "Нет, оставить этот чат");

    private final String callback;

    private final String message;

    TelegramInlineButton(String callback, String message) {
        this.callback = callback;
        this.message = message;
    }

    public static TelegramInlineButton fromData(String data) {
        return Arrays.stream(values())
                .filter(enumButton -> data.startsWith(enumButton.getCallback()))
                .findFirst()
                .orElse(null);
    }
}
