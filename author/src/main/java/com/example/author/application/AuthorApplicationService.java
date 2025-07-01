package com.example.author.application;

import com.example.author.aggregate.Author;
import com.example.author.command.ApproveAuthorRegistration;
import com.example.author.command.RejectAuthorRegistration;
import com.example.author.command.RequestAuthorRegistration;
import com.example.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional("transactionManager")
public class AuthorApplicationService {

    private final AuthorRepository repo;

    public void requestRegistration(RequestAuthorRegistration cmd) {
        // 이제 Command에서 String 타입의 ID를 받아서 전달합니다.
        Author author = Author.register(
                cmd.id(),
                cmd.name(),
                cmd.description(),
                cmd.portfolio()
        );
        repo.save(author);
    }

    public void approveRegistration(ApproveAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.approve();
        repo.save(author);
    }

    public void rejectRegistration(RejectAuthorRegistration cmd) {
        Author author = repo.findById(cmd.getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.reject();
        repo.save(author);
    }
}