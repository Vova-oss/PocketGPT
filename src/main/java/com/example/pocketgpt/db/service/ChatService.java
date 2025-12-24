package com.example.pocketgpt.db.service;

import com.example.pocketgpt.db.entity.Chat;
import com.example.pocketgpt.db.entity.enums.ChatStatus;
import com.example.pocketgpt.db.repository.ChatRepository;
import com.example.pocketgpt.telegram.context.TelegramContext;
import com.example.pocketgpt.telegram.model.TgResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.pocketgpt.db.entity.enums.ChatStatus.DRAFT;

@Service
@RequiredArgsConstructor
public class ChatService {

    @Value("${max.amount.of.chats.by.user}")
    private long maxAmountOfChats;

    private final ChatRepository chatRepository;

    private final UserService userService;

    @Transactional
    public void deactivateActiveChatsByUserId(){
        chatRepository.deactivateActiveChatsByUserId(TelegramContext.get().getTgId());
    }

    @Transactional
    public TgResponse createNewChat() {
        var tgId = TelegramContext.get().getTgId();
        var allChats = chatRepository.findAllChatsByUserId(tgId);

        var draftChat = allChats.stream()
                .filter(chat -> DRAFT == chat.getStatus())
                .findFirst();

        if (allChats.size() >= maxAmountOfChats && draftChat.isEmpty()) {
            return TgResponse.messageMaxAmountOfChats();
        }

        if (draftChat.isEmpty()){
            var user = userService.findByTgId(tgId);
            var chat = new Chat();
            chat.setUser(user);
            chatRepository.save(chat);
        }

        return TgResponse.messageWriteNameOfNewChat();
    }

    @Transactional
    public Optional<Chat> saveDraftChatWithNewName(){
        var tgId = TelegramContext.get().getTgId();
        var draftChat = chatRepository.findDraftChatByUserId(tgId);
        draftChat.ifPresent(chat -> {
            chat.setName(TelegramContext.get().getMessage());
            chat.setStatus(ChatStatus.ACTIVE);
            chatRepository.save(chat);
            chatRepository.deactivateActiveChatsByUserId(TelegramContext.get().getTgId());
        });
        return draftChat;
    }

    @Transactional
    public Optional<Chat> getActiveChatIfExists() {
        var tgId = TelegramContext.get().getTgId();
        return chatRepository.findActiveChatByUserId(tgId);
    }

    @Transactional
    public List<Chat> findNotDraftChatsByUser() {
        var tgId = TelegramContext.get().getTgId();
        return chatRepository.findNotDraftChatsByUserId(tgId);
    }

    @Transactional
    public void activateChatById(String chatId) {
        chatRepository.deactivateActiveChatsByUserId(TelegramContext.get().getTgId());
        chatRepository.activateChatById(Long.valueOf(chatId));
    }

    @Transactional
    public void deleteChatById(String chatId) {
        chatRepository.deleteById(Long.valueOf(chatId));
    }
}
