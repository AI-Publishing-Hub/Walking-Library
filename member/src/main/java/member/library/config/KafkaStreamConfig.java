package member.library.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import member.library.domain.SignedUp;
import member.library.infra.MemberInfoViewHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import java.util.function.Function;

@Configuration
public class KafkaStreamConfig {

    @Autowired
    private MemberInfoViewHandler memberInfoViewHandler;

    /**
     * [수정된 부분]
     * 스프링이 1차 변환을 마친 Message<Object>를 그대로 받습니다.
     * 여기서 Object는 보통 LinkedHashMap 형태입니다.
     */
    @Bean
    public Function<Message<Object>, Message<Object>> processEvent() {
        return message -> {
            try {
                // 어떤 이벤트인지 구분하기 위해 메시지 헤더의 'type'을 확인합니다.
                String eventType = message.getHeaders().get("type", String.class);
                Object payload = message.getPayload();

                // ObjectMapper를 사용하여 Map 형태의 payload를 실제 이벤트 객체로 변환합니다.
                ObjectMapper objectMapper = new ObjectMapper();

                System.out.println("Received Event Type: " + eventType);

                if ("SignedUp".equals(eventType)) {
                    SignedUp signedUp = objectMapper.convertValue(payload, SignedUp.class);
                    memberInfoViewHandler.handleSignedUp(signedUp);
                }
                // else if ("PointConsumed".equals(eventType)) { ... }
                // 나중에 다른 이벤트가 추가되면 여기에 핸들러를 추가하면 됩니다.

            } catch (Exception e) {
                e.printStackTrace();
            }
            // 이 메시지를 다른 곳으로 보낼 필요가 없으면 null을 반환합니다.
            return null;
        };
    }
}