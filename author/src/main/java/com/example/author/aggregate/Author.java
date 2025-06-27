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
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    private UUID id;
    private String name;
    private String description;
    private String portfolio;
    private Instant createdAt;
    private Instant updatedAt;

    // ↓↓ 이 부분을 복붙 ↓↓
    /** 모든 필드를 초기화하는 생성자 */
    public Author(UUID id,
                  String name,
                  String description,
                  String portfolio,
                  Instant createdAt,
                  Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.portfolio = portfolio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    // ↑↑ 복붙 완료 ↑↑

    // 기본 생성자 (JPA가 사용할 수 있도록 반드시 필요)
    public Author() {}

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

    // 필요에 따라 Getter/Setter도 생성하세요 (Alt+Insert → Getter and Setter)
}
