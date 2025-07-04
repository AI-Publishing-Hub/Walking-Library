package book.infra;

import book.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import book.domain.*;
import static book.domain.OpenAiService.*;
import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PolicyHandler {

    private final BookRepository bookRepository;
    private final BookViewHandler bookViewHandler;
    private final ObjectMapper objectMapper;
    private final OpenAiService openAiService; // OpenAiService는 여전히 필요할 수 있으니 남겨둡니다.

    @Bean
    public Consumer<Message<String>> bookEventConsumer() {
        return message -> {
            String eventType = (String) message.getHeaders().get("type");
            System.out.println("##### 수신된 이벤트 타입 : " + eventType + " / 페이로드 : " + message.getPayload() + " #####");

            try {
                if ("BookRegistered".equals(eventType)) {
                    BookRegistered event = objectMapper.readValue(message.getPayload(), BookRegistered.class);

                    System.out.println("AI 분석을 시작합니다...");
                    AiResult result = openAiService.analyzeBook(event);
                    Book book = bookRepository.findById(event.getId()).orElseThrow();
                    book.setSummary(result.summary());
                    book.setPrice(result.price());
                    book.setBookCoverUrl(result.bookCoverUrl());
                    book.setIsBookPublished(true); // 책을 출판 상태로 변경

                    Book savedBook = bookRepository.save(book);

                    System.out.println("AI 분석이 완료되었습니다.");

                    bookViewHandler.whenBookRegistered_then_createView(savedBook);

                } else if ("BestsellerStatusChanged".equals(eventType)) {
                    bookViewHandler.whenBestsellerStatusChanged_then_updateView(
                            objectMapper.readValue(message.getPayload(), BestsellerStatusChanged.class)
                    );
                }
            } catch (Exception e) {
                System.err.println("##### [에러] 이벤트 처리 중 예외 발생 : " + eventType + " #####");
                e.printStackTrace();
            }
        };
    }

    @Bean
    public Consumer<Message<BookAccessChecked>> wheneverBookAccessChecked_Route() {
        return message -> {
            BookAccessChecked event = message.getPayload();
            // userId를 correlationId로 사용하여 대기 중인 스레드에 결과를 전달
            EventSagaRouter.E(event.getUserId(), event);
        };
    }
}