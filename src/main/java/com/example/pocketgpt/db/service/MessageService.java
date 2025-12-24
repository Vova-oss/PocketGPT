package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.entity.Chat;
import com.example.pocketgpt.db.entity.Message;
import com.example.pocketgpt.db.entity.enums.Role;
import com.example.pocketgpt.db.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public List<Message> get10LastMessagesByChatId(Long chatId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        return messageRepository.findByChat_Id(chatId, pageable);
    }

    @Transactional
    public void saveUserMessageAndGptResponse(String gptMessageText, String userMessageText, Chat chat) {
        var userMessage = new Message();
        userMessage.setChat(chat);
        userMessage.setMessageText(userMessageText);
        userMessage.setRole(Role.USER);

        var gptMessage = new Message();
        gptMessage.setChat(chat);
        gptMessage.setMessageText(gptMessageText);
        gptMessage.setRole(Role.ASSISTANT);

        messageRepository.saveAll(List.of(userMessage, gptMessage));
    }
}
