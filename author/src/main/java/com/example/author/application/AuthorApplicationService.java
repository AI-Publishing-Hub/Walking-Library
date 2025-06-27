package com.example.author.application;

import com.example.author.aggregate.Author;
import com.example.author.command.RequestAuthorRegistration;
import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.repository.AuthorRepository;

import com.example.author.spi.EventPublisher;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.List;
import java.time.Instant;


@Service
public class AuthorApplicationService {
    private final AuthorRepository repo;
    private final EventPublisher publisher;

    public AuthorApplicationService(AuthorRepository repo, EventPublisher publisher) {
        this.repo = repo;
        this.publisher = publisher;
    }

    // 1) 등록 요청
    public void requestRegistration(RequestAuthorRegistration cmd) {
        // 새 Author 인스턴스 생성 (UUID 자동 생성 등)
        Author a = new Author(UUID.randomUUID(), cmd.getName(),
                cmd.getDescription(), cmd.getPortfolio(),
                Instant.now(), Instant.now());
        List<Object> events = a.requestRegistration(cmd);
        repo.save(a);
        publisher.publish(events);
    }

    // 2) 승인 처리
    public void approveRegistration(ApproveAuthorRegistration cmd) {
        Author a = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        List<Object> events = a.approveRegistration(cmd);
        repo.save(a);
        publisher.publish(events);
    }

    // 3) 반려 처리
    public void rejectRegistration(RejectAuthorRegistration cmd) {
        Author a = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        List<Object> events = a.rejectRegistration(cmd);
        repo.save(a);
        publisher.publish(events);
    }
}
