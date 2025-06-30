package book.infra;

import book.domain.Book;
import book.domain.BookRepository;
import book.domain.CreatedPost;
import book.domain.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static book.domain.OpenAiService.*;

@Component
@RequiredArgsConstructor
public class BookEventHandler {

    private final BookRepository bookRepository;
    private final OpenAiService openAiService;

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

            bookRepository.save(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

