package com.example.author.application;

import com.example.author.aggregate.Author;
import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.command.RequestAuthorRegistration;
import com.example.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 애그리거트 로딩 → 명령 위임 → 저장 · 이벤트 발행
 * DB 트랜잭션만 사용하도록 'transactionManager'를 명시했다.
 */
@Service
@RequiredArgsConstructor
@Transactional("transactionManager")   // ★ JPA 트랜잭션 매니저만 사용
public class AuthorApplicationService {

    private final AuthorRepository repo;

    /** 작가 등록 요청 */
    public void requestRegistration(RequestAuthorRegistration cmd) {
        Author author = Author.register(
                cmd.name(),          // record accessor
                cmd.description(),
                cmd.portfolio()
        );
        repo.save(author);
    }

    /** 작가 승인 */
    public void approveRegistration(ApproveAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.approve();
        repo.save(author);
    }

    /** 작가 반려 */
    public void rejectRegistration(RejectAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.reject();
        repo.save(author);
    }
}
