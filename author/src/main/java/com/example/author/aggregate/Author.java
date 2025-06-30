package com.example.author.aggregate;

import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.event.AuthorRegistrationApprovedEvent;
import com.example.author.event.AuthorRegistrationRejectedEvent;
import com.example.author.event.AuthorRegistrationRequestedEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "authors")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 이벤트 발행을 위해 AbstractAggregateRoot 상속
public class Author extends AbstractAggregateRoot<Author> {

    @Id
    private UUID id;
    private String name;
    private String description;
    private String portfolio;
<<<<<<< HEAD
    @Enumerated(EnumType.STRING)
    private AuthorStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    // --- 정적 팩토리 메소드: 새로운 작가 등록(생성)을 위한 유일한 공식 창구 ---
    public static Author register(String name, String description, String portfolio) {
        // 1. 새로운 Author 객체를 안전하게 생성합니다.
        Author author = new Author();

        // 2. 초기 상태를 정의하는 이벤트를 만듭니다.
        var event = new AuthorRegistrationRequestedEvent(UUID.randomUUID(), name, description, portfolio);

        // 3. on() 메소드를 호출하여 자기 자신의 상태를 초기화합니다.
        author.on(event);

        // 4. 이벤트를 발행 대기열에 추가합니다. (repo.save() 시점에 발행됨)
        author.registerEvent(event);

        return author;
    }

    // --- 커맨드 핸들러: 상태 변경을 위한 메소드들 ---
    public void approve() {
        if (this.status != AuthorStatus.PENDING) {
            throw new IllegalStateException("승인 대기중인 작가만 승인할 수 있습니다.");
        }
        var event = new AuthorRegistrationApprovedEvent(this.id, this.name);
        this.on(event);
        registerEvent(event);
    }

    public void reject() {
        if (this.status != AuthorStatus.PENDING) {
            throw new IllegalStateException("승인 대기중인 작가만 반려할 수 있습니다.");
        }
        var event = new AuthorRegistrationRejectedEvent(this.id, this.name);
        this.on(event);
        registerEvent(event);
    }

    // --- 이벤트 핸들러: 이벤트를 기반으로 실제 상태를 변경하는 역할 ---
    private void on(AuthorRegistrationRequestedEvent e) {
        this.id = e.id();
        this.name = e.name();
        this.description = e.description();
        this.portfolio = e.portfolio();
        this.status = AuthorStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    private void on(AuthorRegistrationApprovedEvent e) {
        this.status = AuthorStatus.APPROVED;
        this.updatedAt = Instant.now();
    }

    private void on(AuthorRegistrationRejectedEvent e) {
        this.status = AuthorStatus.REJECTED;
        this.updatedAt = Instant.now();
    }
}
=======
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
>>>>>>> 0e0f113f64d493d8f0abce3363e83022f4f2641f
