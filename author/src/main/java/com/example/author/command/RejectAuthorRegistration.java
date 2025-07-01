package com.example.author.command;

import java.util.UUID;

public class RejectAuthorRegistration {
    private UUID id;

    public RejectAuthorRegistration() {}

    public RejectAuthorRegistration(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
