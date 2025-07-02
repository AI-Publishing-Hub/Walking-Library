package com.example.author.web;

import com.example.author.application.AuthorApplicationService;
import com.example.author.command.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID; // 이 import는 이제 필요 없습니다.

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorApplicationService service;

    @PostMapping("/register")
    public void register(@RequestBody RequestAuthorRegistration req) {
        service.requestRegistration(req);
    }

    @PostMapping("/{id}/approve")
    public void approve(@PathVariable String id) { // ★ UUID에서 String으로 변경
        service.approveRegistration(new ApproveAuthorRegistration(id));
    }

    @PostMapping("/{id}/reject")
    public void reject(@PathVariable String id) { // ★ UUID에서 String으로 변경
        service.rejectRegistration(new RejectAuthorRegistration(id));
    }
}