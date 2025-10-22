package com.example.pocketgpt.db.mapper;

import com.example.pocketgpt.db.entity.User;
import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;

public class UserMapper {

    public static User toUser(TelegramUserInfo telegramUserInfo) {
        return User.builder()
                .username(telegramUserInfo.getUsername())
                .firstName(telegramUserInfo.getFirstName())
                .tgId(telegramUserInfo.getTgId())
                .lastName(telegramUserInfo.getLastName())
                .build();
    }


}
