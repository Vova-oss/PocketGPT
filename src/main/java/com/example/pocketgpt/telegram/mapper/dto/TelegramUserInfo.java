package com.example.pocketgpt.telegram.mapper.dto;

import com.example.pocketgpt.telegram.model.TelegramCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TelegramUserInfo {

    private Long tgId;

    private Long chatId;

    private String username;

    private String firstName;

    private String lastName;

    private String messageText;

    private TelegramCommand command;

}
