package com.example.pocketgpt.repository;

import com.example.pocketgpt.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
