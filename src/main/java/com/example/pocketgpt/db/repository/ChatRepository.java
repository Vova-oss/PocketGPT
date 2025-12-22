package com.example.pocketgpt.db.repository;

import com.example.pocketgpt.db.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "update chats set status = 'INACTIVE' where user_id = :userId and status = 'ACTIVE'")
    void deactivateActiveChatsByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from chats where user_id = :userId and status = 'DRAFT'")
    Optional<Chat> findDraftChatByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from chats where user_id = :userId and status = 'ACTIVE'")
    Optional<Chat> findActiveChatByUserId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "select * from chats where user_id = :userId")
    List<Chat> findChatsByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(nativeQuery = true, value = "update chats set status = 'ACTIVE' where id = :id")
    void activateChatById(@Param("id") Long id);
}
