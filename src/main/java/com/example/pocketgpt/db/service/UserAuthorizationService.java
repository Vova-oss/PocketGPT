package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.mapper.UserMapper;
import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthorizationService {

    private final UserService userService;

    @Transactional
    public void authorizationUser(TelegramUserInfo userInfo) {
        var user = userService.findByTgId(userInfo.getTgId());

        if (user == null) {
            user = UserMapper.toUser(userInfo);
            userService.save(user);
        }
    }

}
