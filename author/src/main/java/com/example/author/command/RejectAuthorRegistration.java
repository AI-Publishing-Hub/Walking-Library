package com.example.author.command;

import lombok.Getter;
import java.util.UUID; // 이 import는 이제 필요 없습니다.

@Getter
public class RejectAuthorRegistration {
    private String id; // ★ UUID에서 String으로 변경

    public RejectAuthorRegistration() {}

    public RejectAuthorRegistration(String id) { // ★ UUID에서 String으로 변경
        this.id = id;
    }
}