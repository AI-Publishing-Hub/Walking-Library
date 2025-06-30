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