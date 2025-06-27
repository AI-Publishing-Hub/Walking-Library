package com.example.author.aggregate;

import com.example.author.command.RequestAuthorRegistration;
import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.event.AuthorRegistrationRequestedEvent;
import com.example.author.event.AuthorRegistrationApprovedEvent;
import com.example.author.event.AuthorRegistrationRejectedEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Author {
    private UUID id;
    private String name;
    private String description;
    private String portfolio;
    private Instant createdAt;
    private Instant updatedAt;

    // 기본 생성자, 모든 필드 생성자 등은 Alt+Insert → Constructor 로 자동 생성 가능
    // Getter/Setter도 Alt+Insert → Getter and Setter

    // 1) 작가 등록 요청 처리
    public List<Object> requestRegistration(RequestAuthorRegistration cmd) {
        // 검증 로직 추가…
        return List.of(new AuthorRegistrationRequestedEvent(id, cmd.getName()));
    }

    // 2) 등록 승인 처리
    public List<Object> approveRegistration(ApproveAuthorRegistration cmd) {
        return List.of(new AuthorRegistrationApprovedEvent(id, name));
    }

    // 3) 등록 반려 처리
    public List<Object> rejectRegistration(RejectAuthorRegistration cmd) {
        return List.of(new AuthorRegistrationRejectedEvent(id, name));
    }

    // 4) 이벤트 핸들러: 상태 변경
    public void on(AuthorRegistrationRequestedEvent e) {
        // e 를 바탕으로 상태 업데이트
    }
    public void on(AuthorRegistrationApprovedEvent e) {
        // 상태 업데이트
    }
    public void on(AuthorRegistrationRejectedEvent e) {
        // 상태 업데이트
    }
}
