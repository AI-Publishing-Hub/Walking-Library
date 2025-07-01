package com.example.author.consumer;

import com.example.author.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorEventsListener {

    @KafkaListener(
            topics = {
                    "author.registration.requested",
                    "author.registration.approved",
                    "author.registration.rejected"
            },
            groupId = "author-service",           // 컨슈머 그룹
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAuthorEvents(@Payload Object event) {
        log.info("🔔 Received event: {}", event);

        // 실제 서비스 연동 로직은 여기서 호출
        // ex) EmailService.notifyAuthorApproved(...)
    }
}
