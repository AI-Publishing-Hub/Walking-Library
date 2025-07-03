package com.example.author.web;

import com.example.author.application.AuthorApplicationService;
import com.example.author.command.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorApplicationService service;

    // ① 작가 등록 요청
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody RequestAuthorRegistration req) {
        service.requestRegistration(req);
        return ResponseEntity.accepted().build();
    }

    // ② 승인
    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID id) {
        service.approveRegistration(new ApproveAuthorRegistration(id));
        return ResponseEntity.ok().build();
    }

    // ③ 반려
    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(@PathVariable UUID id) {
        service.rejectRegistration(new RejectAuthorRegistration(id));
        return ResponseEntity.ok().build();
    }
}
