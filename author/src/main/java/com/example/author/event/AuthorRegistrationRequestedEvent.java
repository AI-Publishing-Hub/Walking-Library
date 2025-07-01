package com.example.author.event;

import java.util.UUID;

public record AuthorRegistrationRequestedEvent(
        UUID id,
        String name,
        String description,
        String portfolio
) {}
