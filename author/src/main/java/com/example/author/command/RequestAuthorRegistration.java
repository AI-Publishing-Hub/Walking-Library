// author/src/main/java/com/example/author/command/RequestAuthorRegistration.java
package com.example.author.command;

/**
 * 작가 등록 요청 DTO
 */
public record RequestAuthorRegistration(
        String name,
        String description,
        String portfolio
) {}
