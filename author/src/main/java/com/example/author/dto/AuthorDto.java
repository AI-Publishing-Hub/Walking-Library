package com.example.author.dto;

import com.example.author.aggregate.Author;
import com.example.author.aggregate.AuthorStatus;
import lombok.Value;

import java.time.Instant;

@Value
public class AuthorDto {
    String id;
    String name;
    String description;
    String portfolio;
    AuthorStatus status;
    Instant createdAt;

    public static AuthorDto from(Author author) {
        return new AuthorDto(
                author.getId().toString(),  // ✅ 이 부분만 수정
                author.getName(),
                author.getDescription(),
                author.getPortfolio(),
                author.getStatus(),
                author.getCreatedAt()
        );
    }
}