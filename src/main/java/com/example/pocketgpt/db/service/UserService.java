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

    @Cacheable(value = "users", key = "#tgId")
    public User findByTgId(Long tgId) {
        return userRepository
                .findByTgId(tgId)
                .orElse(null);
    }

    @CachePut(value = "users", key = "#result.tgId")
    public User save(User user) {
        return userRepository.save(user);
    }

}
