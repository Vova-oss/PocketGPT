package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.entity.Chat;
import com.example.pocketgpt.db.entity.enums.ChatStatus;
import com.example.pocketgpt.db.repository.ChatRepository;
import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.mapper.dto.TelegramUserInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        var chats = chatRepository.findDraftChatByTgId(tgId);
        if (chats.isEmpty()){
            var user = userService.findByTgId(tgId);
            var chat = new Chat();
            chat.setUser(user);
            chatRepository.save(chat);
        }
    }

    @Transactional
    public Optional<Chat> saveDraftChatWithNewName(TelegramUserInfo userInfo,Long tgId){
        var draftChat = chatRepository.findDraftChatByTgId(tgId);
        draftChat.ifPresent(chat -> {
            chat.setName(userInfo.getMessageText());
            chat.setStatus(ChatStatus.ACTIVE);
            chatRepository.save(chat);
        });
        return draftChat;
    }

}
