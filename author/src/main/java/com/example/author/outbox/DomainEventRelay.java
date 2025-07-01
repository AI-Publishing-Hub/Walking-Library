package com.example.author.outbox;

import com.example.author.event.AuthorRegistrationApprovedEvent;
import com.example.author.event.AuthorRegistrationRejectedEvent;
import com.example.author.event.AuthorRegistrationRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

/**
 * DB 트랜잭션 커밋 후(AFTER_COMMIT) 도메인 이벤트를 Kafka 로 전파한다.
 * KafkaTemplate.executeInTransaction() 으로 Kafka 트랜잭션을 함께 묶는다.
 */
@Service
@RequiredArgsConstructor
public class DomainEventRelay {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void relay(Object event) {

        String topic;
        if (event instanceof AuthorRegistrationRequestedEvent) {
            topic = "author.registration.requested";
        } else if (event instanceof AuthorRegistrationApprovedEvent) {
            topic = "author.registration.approved";
        } else if (event instanceof AuthorRegistrationRejectedEvent) {
            topic = "author.registration.rejected";
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getClass());
        }

        kafkaTemplate.executeInTransaction(kt -> {
            kt.send(topic, event);
            return null;   // executeInTransaction 리턴값 약속
        });
    }
}
