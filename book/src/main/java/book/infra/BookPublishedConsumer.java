package book.infra;

import book.domain.BookInfo;
import book.domain.BookPublished;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class BookPublishedConsumer {

    private final BookInfoRepository bookInfoRepository;

    @Bean
    public Consumer<BookPublished> library() {
        return event -> {
            BookInfo info = new BookInfo();
            info.setId(event.getId());
            info.setTitle(event.getTitle());
            info.setViewCount(event.getViewCount());
            info.setCreatedAt(event.getCreatedAt());
            info.setUpdatedAt(event.getUpdatedAt());
            info.setPrice(event.getPrice());
            info.setBookCoverUrl(event.getBookCoverUrl());
            info.setSummary(event.getSummary());
            info.setAuthorId(event.getAuthorId());
            info.setIsBestSeller(event.getIsBestseller());

            bookInfoRepository.save(info);
            System.out.println("✅ BookInfo 저장 완료 from Kafka (library topic): " + event.getId());
        };
    }
}
