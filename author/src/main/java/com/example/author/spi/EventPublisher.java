package com.example.author.spi;

import java.util.List;

public interface EventPublisher {
    /**
     * 이벤트를 외부 버스(Kafka, RabbitMQ 등)에 발행
     * @param events 발행할 이벤트 객체 목록
     */
    void publish(List<Object> events);
}
