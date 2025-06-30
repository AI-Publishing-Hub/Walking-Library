package com.example.author.application;

import com.example.author.aggregate.Author;
import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.command.RequestAuthorRegistration;
import com.example.author.repository.AuthorRepository;
import com.example.author.spi.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorApplicationService {

    private final AuthorRepository repo;
    // EventPublisher는 나중에 Kafka 어댑터에서 실제 구현됩니다.
    // private final EventPublisher publisher; // repo.save()가 이벤트를 발행하므로 당장은 필요 없습니다.

    @Transactional
    public void requestRegistration(RequestAuthorRegistration cmd) {
        // Author.register() 라는 공식 창구를 통해 안전하게 Author 객체 생성
        Author author = Author.register(
                cmd.getName(),
                cmd.getDescription(),
                cmd.getPortfolio()
        );
        // repo.save()가 호출될 때, AbstractAggregateRoot가 등록된 이벤트를 감지하고 발행해줍니다.
        repo.save(author);
    }

    @Transactional
    public void approveRegistration(ApproveAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.approve(); // 애그리거트의 비즈니스 메소드 호출 (상태 변경 + 이벤트 등록)

        repo.save(author); // 변경된 상태를 저장하고, 등록된 이벤트를 발행
    }

    @Transactional
    public void rejectRegistration(RejectAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.reject(); // 애그리거트의 비즈니스 메소드 호출

        repo.save(author);
    }
}