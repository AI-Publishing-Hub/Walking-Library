package com.example.author.event;

import java.util.UUID; // 이 import는 이제 필요 없습니다.

public record AuthorRegistrationApprovedEvent(String id, String name) {} // ★ UUID에서 String으로 변경