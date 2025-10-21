package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.entity.User;
import com.example.pocketgpt.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#telegramId")
    public User findByTelegramId(Long telegramId) {
        return userRepository
                .findByTelegramId(telegramId)
                .orElse(null);
    }

    @CachePut(value = "users", key = "#result.telegramId")
    public User save(User user) {
        return userRepository.save(user);
    }

}
