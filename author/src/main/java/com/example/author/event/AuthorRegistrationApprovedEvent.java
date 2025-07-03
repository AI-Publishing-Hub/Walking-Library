package com.example.author.event;

import java.util.UUID;

public class AuthorRegistrationApprovedEvent {
    private UUID id;
    private String name;

    // 기본 생성자 (프레임워크용)
    public AuthorRegistrationApprovedEvent() {}

    // 모든 필드 생성자
    public AuthorRegistrationApprovedEvent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
