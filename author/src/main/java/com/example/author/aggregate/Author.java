package com.example.author.aggregate;

import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.event.AuthorRegistrationApprovedEvent;
import com.example.author.event.AuthorRegistrationRejectedEvent;
import com.example.author.event.AuthorRegistrationRequestedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.Instant;
import java.util.UUID; // 이 import는 이제 필요 없습니다.

@Entity
@Table(name = "authors")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author extends AbstractAggregateRoot<Author> {

    @Id
    private String id; // ★ UUID에서 String으로 변경

    private String name;
    private String description;
    private String portfolio;
    @Enumerated(EnumType.STRING)
    private AuthorStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public static Author register(String id, String name, String description, String portfolio) {
        Author author = new Author();
        var event = new AuthorRegistrationRequestedEvent(id, name, description, portfolio);
        author.on(event);
        author.registerEvent(event);
        return author;
    }

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

    private void on(AuthorRegistrationRequestedEvent e) {
        this.id = e.id(); // ★ 이제 String 타입
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