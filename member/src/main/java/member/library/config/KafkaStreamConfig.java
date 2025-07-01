package member.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import member.library.infra.MemberInfoViewHandler;
import member.library.domain.SignedUp;

@Configuration
public class KafkaStreamConfig {
    @Autowired
    private MemberInfoViewHandler memberInfoViewHandler;

    @Bean
    public Function<Message<Object>, Message<Object>> processEvent() {
        return message -> {
            try {
                Object payload = message.getPayload();
                ObjectMapper objectMapper = new ObjectMapper();
                SignedUp signedUp = objectMapper.convertValue(payload, SignedUp.class);

                memberInfoViewHandler.handleSignedUp(signedUp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null; // 메시지 그대로 내보낼 필요 없으면 null 반환 가능
        };
    }
}