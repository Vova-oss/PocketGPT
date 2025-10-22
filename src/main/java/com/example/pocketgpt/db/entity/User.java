package com.example.pocketgpt.db.entity;

import com.example.pocketgpt.db.entity.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "tg_id", nullable = false, unique = true)
    private Long tgId;

    @Column(name = "tg_chat_id")
    private Long tgChatId;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "language_code", length = 10)
    private String languageCode;

    @Column(name = "state", length = 20)
    @Enumerated(EnumType.STRING)
    private UserState state = UserState.IDLE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
