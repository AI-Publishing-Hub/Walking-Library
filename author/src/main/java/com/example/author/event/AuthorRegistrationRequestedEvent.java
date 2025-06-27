package com.example.author.event;

import java.util.UUID;

public class AuthorRegistrationRequestedEvent {
    private UUID id;
    private String name;

    public AuthorRegistrationRequestedEvent() {}

    public AuthorRegistrationRequestedEvent(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
}
