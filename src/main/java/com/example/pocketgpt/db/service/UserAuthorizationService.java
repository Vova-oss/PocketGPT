package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UserAuthorizationService {

    private final UserService userService;

    @Transactional
    public void authorizationUser(Update update) {
        var tgId = update.getMessage().getFrom().getId();
        var user = userService.findByTgId(tgId);

        if (user == null) {
            user = UserMapper.toUser(update);
            userService.save(user);
        }
    }

}
