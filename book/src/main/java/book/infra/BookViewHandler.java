package book.infra;

import book.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BookViewHandler {

    @Autowired
    private BookViewRepository bookViewRepository;

    public void whenBookRegistered_then_createView(Book book) {
        if(book == null) return;

        BookView bookView = new BookView();
        bookView.setId(book.getId());
        bookView.setTitle(book.getTitle());
        bookView.setAuthorId(book.getAuthorId());
        bookView.setIsBookPublished(book.getIsBookPublished());
        bookView.setSummary(book.getSummary());
        bookView.setPrice(book.getPrice());
        bookView.setBookCoverUrl(book.getBookCoverUrl());
        bookView.setIsBestseller(book.getIsBestseller());
        bookView.setViewCount(book.getViewCount());
        bookView.setCreatedAt(book.getCreatedAt());
        bookView.setUpdatedAt(book.getUpdatedAt());
        bookView.setContent(book.getContent());

        bookViewRepository.save(bookView);
    }

    public void whenBestsellerStatusChanged_then_updateView(BestsellerStatusChanged event) {
        if (event == null || !event.validate()) return;

        bookViewRepository.findById(event.getId()).ifPresent(bookView -> {
            bookView.setIsBestseller(event.getIsBestseller());
            bookView.setViewCount(event.getViewCount());
            bookViewRepository.save(bookView);
        });
    }
}