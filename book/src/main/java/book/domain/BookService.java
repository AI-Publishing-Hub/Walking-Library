package book.domain;

import book.infra.BookPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BookPublisher bookPublisher;

    public Book create(Book book) {
        Book saved = bookRepository.save(book);
        eventPublisher.publishEvent(new CreatedPost(saved.getId(), saved.getTitle(), saved.getContent()));
        return saved;
    }

    public void publishBook(Book book) {
        if (book.getSummary() != null &&
                book.getBookCoverUrl() != null &&
                book.getPrice() != null) {

            book.setIsBookPublished(true);
            bookRepository.save(book);

            BookPublished event = new BookPublished(
                    book.getId(),
                    book.getTitle(),
                    book.getContent(),
                    book.getViewCount(),
                    book.getCreatedAt(),
                    book.getUpdatedAt(),
                    book.getPrice(),
                    book.getBookCoverUrl(),
                    book.getSummary(),
                    book.getAuthorId(),
                    book.getIsBestseller()
            );

            bookPublisher.publish(event); // → Kafka로 비동기 발행됨
        }
    }
}
