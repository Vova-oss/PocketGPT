package com.example.pocketgpt.db.repository;

import com.example.pocketgpt.db.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "update chats set status = 'INACTIVE' where user_id = :tgId and status = 'ACTIVE'")
    void deactivateActiveChatsByUserId(@Param("tgId") Long tgId);

    @Query(nativeQuery = true, value = "select * from chats where user_id = :tgId and status = 'DRAFT'")
    Optional<Chat> findDraftChatByTgId(@Param("tgId") Long tgId);


}
