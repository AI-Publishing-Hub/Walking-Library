package com.example.author.dto;

import com.example.author.aggregate.Author;
import com.example.author.aggregate.AuthorStatus;
import lombok.Value;

import java.time.Instant;
import java.util.UUID; // 이 import는 이제 필요 없습니다.

@Value
public class AuthorDto {
    String id; // ★ UUID에서 String으로 변경
    String name;
    String description;
    String portfolio;
    AuthorStatus status;
    Instant createdAt;

    public static AuthorDto from(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getDescription(),
                author.getPortfolio(),
                author.getStatus(),
                author.getCreatedAt()
        );
    }
}