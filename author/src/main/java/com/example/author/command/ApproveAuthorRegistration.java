package com.example.author.command;

import java.util.UUID;

public class ApproveAuthorRegistration {
    private UUID id;

    public ApproveAuthorRegistration() {}

    public ApproveAuthorRegistration(UUID id) {
        this.id = id;
    }

    public UUID getId() { return id; }
}
