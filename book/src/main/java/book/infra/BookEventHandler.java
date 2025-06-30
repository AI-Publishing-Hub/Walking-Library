package book.infra;

import book.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static book.domain.OpenAiService.*;

@Component
@RequiredArgsConstructor
public class BookEventHandler {

    private final BookRepository bookRepository;
    private final OpenAiService openAiService;
    private final BookService bookService;

    @EventListener
    public void handleCreatedPost(CreatedPost event) {
        try {
            AiResult result = openAiService.analyzeBook(event.getTitle(), event.getContent());

            Book book = bookRepository.findById(event.getId()).orElseThrow();
            book.setSummary(result.summary());
            System.out.println(result.summary());
            book.setPrice(result.price());
            System.out.println(result.price());
            book.setBookCoverUrl(result.bookCoverUrl());
            System.out.println(result.bookCoverUrl());

            Book updated = bookRepository.save(book);

            // 출간 폴리시 호출
            bookService.publishBook(updated);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

