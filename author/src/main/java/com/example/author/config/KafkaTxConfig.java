package com.example.author.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * KafkaTemplate 에서 executeInTransaction() 을 사용할 수 있도록
 * ProducerFactory 에 트랜잭션 ID 프리픽스를 지정한다.
 */
@Configuration
public class KafkaTxConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        // ★ 핵심: 트랜잭션 ID 프리픽스 지정
        DefaultKafkaProducerFactory<String, Object> pf =
                new DefaultKafkaProducerFactory<>(props);
        pf.setTransactionIdPrefix("author-tx-");   // executeInTransaction() 사용 가능!

        return pf;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(
            ProducerFactory<String, Object> pf
    ) {
        return new KafkaTemplate<>(pf);
    }
}
