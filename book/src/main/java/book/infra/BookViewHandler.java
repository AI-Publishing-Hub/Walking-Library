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

    public void whenBookRegistered_then_createView(BookRegistered event) {
        if (event == null || !event.validate()) return;

        BookView bookView = new BookView();
        bookView.setId(event.getId());
        bookView.setTitle(event.getTitle());
        bookView.setPrice(event.getPrice());
        bookView.setAuthorId(event.getAuthorId());
        bookView.setIsBookPublished(event.getIsBookPublished());
        // 초기값 설정
        bookView.setViewCount(0L);
        bookView.setIsBestseller(false);

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