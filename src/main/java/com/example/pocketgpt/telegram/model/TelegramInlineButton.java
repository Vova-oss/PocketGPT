package com.example.pocketgpt.telegram.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TelegramInlineButton {

    LIST_DIALOGS("list_dialogs", "Мои диалоги"),

    NEW_DIALOG("new_dialog", "Новый диалог"),

    DELETE_DIALOG("delete_dialog", "Удалить диалог"),

    CONTINUE_DIALOG("continue_dialog", "Продолжить диалог");

    private final String url;

    private final String message;

    TelegramInlineButton(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public static TelegramInlineButton fromData(String data) {
        return Arrays.stream(values())
                .filter(enumButton -> enumButton.getUrl().equalsIgnoreCase(data))
                .findFirst()
                .orElse(null);
    }
}
