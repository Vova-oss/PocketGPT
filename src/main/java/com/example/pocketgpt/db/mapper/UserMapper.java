package com.example.pocketgpt.db.mapper;

import com.example.pocketgpt.db.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserMapper {

    public static User toUser(Update update) {
        var message = update.getMessage();
        var from = message.getFrom();

        return User.builder()
                .username(from.getUserName())
                .firstName(from.getFirstName())
                .tgId(from.getId())
                .lastName(from.getUserName())
                .build();
    }

}
