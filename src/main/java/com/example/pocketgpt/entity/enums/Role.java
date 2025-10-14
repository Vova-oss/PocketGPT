package com.example.pocketgpt.entity.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system");

    private final String value;

    Role(String value) {
        this.value = value;
    }

}
