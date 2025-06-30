package book.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher eventPublisher;

    public Book create(Book book) {
        Book saved = bookRepository.save(book);
        eventPublisher.publishEvent(new CreatedPost(saved.getId(), saved.getTitle(), saved.getContent()));
        return saved;
    }
}
