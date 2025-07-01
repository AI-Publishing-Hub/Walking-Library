package com.example.author.command;

// record accessor를 사용하기 위해 record 타입 유지
public record RequestAuthorRegistration(
        String id,      // ★ UUID에서 String으로 변경하고 필드 추가
        String name,
        String description,
        String portfolio
) {}