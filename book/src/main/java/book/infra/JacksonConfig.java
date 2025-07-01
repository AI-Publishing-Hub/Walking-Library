package book.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    /**
     * 애플리케이션 전역에서 사용할 ObjectMapper를 설정합니다.
     * @return 커스터마이징된 ObjectMapper
     */
    @Bean
    @Primary // 이 ObjectMapper를 기본으로 사용하도록 강제합니다.
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Java 8의 날짜/시간(LocalDateTime 등) 타입을 올바르게 처리하기 위해 JavaTimeModule을 등록합니다.
        objectMapper.registerModule(new JavaTimeModule());

        // 날짜/시간을 ISO-8601 형식의 문자열로 직렬화하도록 설정합니다. (e.g., "2025-06-30T10:14:50.547")
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
}
