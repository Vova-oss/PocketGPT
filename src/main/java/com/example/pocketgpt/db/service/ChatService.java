package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.entity.Chat;
import com.example.pocketgpt.db.entity.enums.ChatStatus;
import com.example.pocketgpt.db.repository.ChatRepository;
import com.example.pocketgpt.telegram.context.TelegramContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private ChatRepository chatRepository;

    private UserService userService;

    @Transactional
    public void deactivateActiveChatsByUserId(){
        chatRepository.deactivateActiveChatsByUserId(TelegramContext.get().getTgId());
    }

    @Transactional
    public void createNewChat() {
        var tgId = TelegramContext.get().getTgId();
        var chats = chatRepository.findDraftChatByUserId(tgId);
        if (chats.isEmpty()){
            var user = userService.findByTgId(tgId);
            var chat = new Chat();
            chat.setUser(user);
            chatRepository.save(chat);
        }
    }

    @Transactional
    public Optional<Chat> saveDraftChatWithNewName(){
        var tgId = TelegramContext.get().getTgId();
        var draftChat = chatRepository.findDraftChatByUserId(tgId);
        draftChat.ifPresent(chat -> {
            chat.setName(TelegramContext.get().getMessage());
            chat.setStatus(ChatStatus.ACTIVE);
            chatRepository.save(chat);
        });
        return draftChat;
    }

    @Transactional
    public Optional<Chat> getActiveChatIfExists() {
        var tgId = TelegramContext.get().getTgId();
        return chatRepository.findActiveChatByUserId(tgId);
    }

    @Transactional
    public List<Chat> findAllDialogsByUser() {
        var tgId = TelegramContext.get().getTgId();
        return chatRepository.findChatsByUserId(tgId);
    }

    @Transactional
    public void activateChatById(String chatId) {
        chatRepository.activateChatById(Long.valueOf(chatId));
    }

    @Transactional
    public void deleteChatById(String chatId) {
        chatRepository.deleteById(Long.valueOf(chatId));
    }
}
