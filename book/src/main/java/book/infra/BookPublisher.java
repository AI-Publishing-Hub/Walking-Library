package book.infra;

import book.domain.BookPublished;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class BookPublisher {

    private final BlockingQueue<BookPublished> queue = new LinkedBlockingQueue<>();

    public void publish(BookPublished event) {
        System.out.println("📤 BookPublished 발행 대기: " + event.getTitle());
        queue.offer(event);  // Kafka 발행 대기열에 추가
    }

    @Bean
    public Supplier<BookPublished> publishBookEvent() {
        return () -> {
            BookPublished event = queue.poll();
            if (event != null) {
                System.out.println("🚀 Kafka로 전송: " + event.getTitle());
            }
            return event;
        };
    }
}
