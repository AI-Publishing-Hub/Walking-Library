package com.example.author.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * 컨슈머 팩토리 + 재시도 / 오류 처리(DefaultErrorHandler) 설정.
 * AuthorEventsListener 가 이 설정을 공유한다.
 */
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // JsonDeserializer: 모든 패키지를 신뢰하도록 지정 (개발 환경에서만 사용)
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory(ConsumerFactory<String, Object> cf) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);

        // ── 재시도 & 오류 처리 정책 ───────────────────────────────
        ExponentialBackOff backOff = new ExponentialBackOff(2000L, 2.0); // 2s → 4s → 8s …
        backOff.setMaxInterval(10000L);                                   // 최대 10s
        backOff.setMaxElapsedTime(30000L);                                // 총 30s 동안 재시도

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(backOff);
        factory.setCommonErrorHandler(errorHandler);

        // (선택) Dead-Letter 토픽을 쓰려면 아래 주석 해제 후 KafkaTemplate 주입
        // errorHandler.addDeadLetterPublishingRecoverer(
        //     new DeadLetterPublishingRecoverer(kafkaTemplate)
        // );

        return factory;
    }
}
