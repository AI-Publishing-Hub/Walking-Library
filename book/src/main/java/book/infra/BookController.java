package book.infra;

import book.domain.*;
import book.dto.BookDetailDto;
import book.dto.BookSummaryDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookViewRepository bookViewRepository;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            // 전달받은 book 객체를 저장합니다.

            Book savedBook = bookRepository.save(book);
            // @PostPersist에 의해 BookRegistered 이벤트가 발행됩니다.
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (Exception e) {
            // 오류 발생 시 서버 에러를 반환합니다.
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 기능 1: 출판된 모든 책 목록 조회 (Read Model 사용)
     */
    @GetMapping("/published")
    public ResponseEntity<List<BookSummaryDto>> getPublishedBooks() {
        List<BookView> bookViews = bookViewRepository.findAllByIsBookPublishedTrue();
        List<BookSummaryDto> bookSummaries = bookViews.stream()
                .map(view -> new BookSummaryDto(
                        view.getId(), view.getTitle(), view.getAuthorId(), view.getIsBestseller(),
                        view.getSummary(), view.getViewCount(), view.getBookCoverUrl(), view.getPrice()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookSummaries);
    }

    /**
     * 기능 2: 베스트셀러 목록 조회 (Read Model 사용)
     */
    @GetMapping("/bestsellers")
    public ResponseEntity<List<BookSummaryDto>> getBestsellerBooks() {
        List<BookView> bookViews = bookViewRepository.findAllByIsBestsellerTrue();
        List<BookSummaryDto> bestsellerSummaries = bookViews.stream()
                .map(view -> new BookSummaryDto(
                        view.getId(), view.getTitle(), view.getAuthorId(), view.getIsBestseller(),
                        view.getSummary(), view.getViewCount(), view.getBookCoverUrl(), view.getPrice()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bestsellerSummaries);
    }

    /**
     * 기능 3: 특정 책 상세 조회 (Saga + Read Model 사용)
     */
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getBookDetails(
            @PathVariable Long id,
            @RequestHeader(value = "X-USER-ID", required = true) String userId) {
        // try {
        // --- 접근 제어 Saga 로직 (기존과 동일) ---
        BookAccessRequested event = new BookAccessRequested();
        event.setId(id);
        // 책 가격은 Write Model 또는 Read Model 어디서든 가져올 수 있습니다.
        event.setPrice(bookViewRepository.findById(id).map(BookView::getPrice).orElse(0));
        event.setUserId(userId);

        EventSagaRouter.V(userId);
        event.publish();
        // BookAccessChecked resultEvent = (BookAccessChecked) EventSagaRouter.P(userId);
        // // --- Saga 로직 끝 ---

        // resultEvent.setAllowed(true);
        //resultEvent != null && resultEvent.isAllowed()
        if (true) {
            // 권한이 있으면, 조회수 증가를 위해 Write Model의 book 객체를 찾습니다.
            bookRepository.findById(id).ifPresent(book -> {
                book.setViewCount(book.getViewCount() + 1);
                bookRepository.save(book); // save를 통해 @PreUpdate(베스트셀러 로직)가 호출되고 이벤트가 발행됨
            });

            // 사용자에게 보여줄 데이터는 Read Model에서 조회하여 즉시 반환합니다.
            return bookViewRepository.findById(id)
                    .map(view -> {
                        BookDetailDto bookDetail = new BookDetailDto(
                                view.getId(), view.getTitle(), view.getContent(),
                                view.getViewCount(), view.getAuthorId(), view.getIsBestseller(),
                                view.getBookCoverUrl(), view.getPrice()
                        );
                        return ResponseEntity.ok(bookDetail);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.status(403).body("Access Denied");
        }

        // } catch (InterruptedException e) {
        //     Thread.currentThread().interrupt();
        //     return ResponseEntity.status(503).body("Service unavailable or timeout");
        // }
    }

//    /**
//     * 기능 3: 특정 책 상세 조회
//     * ID로 책을 조회하고, 조회수를 1 증가시킨 후 지정된 필드를 반환합니다.
//     * 조회수 증가 후 베스트셀러 요건(조회수 30 이상) 충족 시 상태가 자동으로 업데이트됩니다.
//     * @param id 책 ID
//     * @return 책 상세 정보
//     */
//    @GetMapping("/{id}/details")
//    public ResponseEntity<BookDetailDto> getBookDetails(@PathVariable Long id) {
//        return bookRepository.findById(id)
//                .map(book -> {
//                    // 조회수 1 증가
//                    book.setViewCount(book.getViewCount() + 1);
//
//                    // save를 통해 @PreUpdate 콜백(베스트셀러 상태 업데이트 로직)이 트리거됩니다.
//                    Book updatedBook = bookRepository.save(book); //
//
//                    BookDetailDto bookDetail = new BookDetailDto(
//                            updatedBook.getId(),
//                            updatedBook.getTitle(),
//                            updatedBook.getContent(),
//                            updatedBook.getViewCount(),
//                            updatedBook.getAuthorId(),
//                            updatedBook.getIsBestseller(),
//                            updatedBook.getBookCoverUrl(),
//                            updatedBook.getPrice()
//                    );
//                    return ResponseEntity.ok(bookDetail);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
}