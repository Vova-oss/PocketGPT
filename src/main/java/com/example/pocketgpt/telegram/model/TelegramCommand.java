package com.example.pocketgpt.telegram.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TelegramCommand {

    START("/start");

    private final String command;

    TelegramCommand(String command) {
        this.command = command;
    }

    public static TelegramCommand fromMessageText(String messageText) {
        return Arrays.stream(values())
                .filter(enumCommand -> enumCommand.getCommand().equalsIgnoreCase(messageText))
                .findFirst()
                .orElse(null);
    }
}
