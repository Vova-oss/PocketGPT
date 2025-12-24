package com.example.pocketgpt.gpt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GptMessage {

    private String role;

    private String content;

    @Override
    public String toString() {
        return "{" +
                "\"role\": \"" + role + '\"' +
                ", \"content\": \"" + content + '\"' +
                '}';
    }
}
