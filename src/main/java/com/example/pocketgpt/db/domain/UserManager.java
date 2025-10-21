package com.example.pocketgpt.db.domain;

import com.example.pocketgpt.db.mapper.UserMapper;
import com.example.pocketgpt.db.service.UserService;
import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManager {

    private final UserService userService;

    public void authorizationUser(TelegramUserInfo userInfo) {
        var user = userService.findByTelegramId(userInfo.getTelegramId());

        if (user == null) {
            user = UserMapper.toUser(userInfo);
            userService.save(user);
        }
    }

}
