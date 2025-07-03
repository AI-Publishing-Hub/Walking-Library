package com.example.author.event;

import java.util.UUID;

public class AuthorRegistrationRejectedEvent {
    private UUID id;
    private String name;

    public AuthorRegistrationRejectedEvent() {}

    public AuthorRegistrationRejectedEvent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
