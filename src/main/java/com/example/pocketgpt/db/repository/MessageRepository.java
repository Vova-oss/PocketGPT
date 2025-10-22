package com.example.pocketgpt.db.repository;

import com.example.pocketgpt.db.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
