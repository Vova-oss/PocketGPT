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

    public static TelegramCommand fromText(String text) {
        return Arrays.stream(values())
                .filter(enumCommand -> enumCommand.getCommand().equalsIgnoreCase(text))
                .findFirst()
                .orElse(null);
    }
}
