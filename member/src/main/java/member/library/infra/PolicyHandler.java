package member.library.infra;

import member.library.domain.BookViewed;
import member.library.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class PolicyHandler {

    private static final Logger log = LoggerFactory.getLogger(PolicyHandler.class);

    // 책 열람 이벤트 수신 → 포인트 차감 트리거
    @Bean
    public Consumer<BookViewed> wheneverBookViewed_ConsumePoints() {
        return event -> {
            try {
                if (!event.validate()) return;

                log.info("📚 BookViewed event received: {}", event);

                User.consumePoints(event);

                log.info("✅ User points consumed successfully for BookViewed: {}", event);

            } catch (Exception e) {
                log.error("❌ Failed to process BookViewed event", e);
            }
        };
    }
}