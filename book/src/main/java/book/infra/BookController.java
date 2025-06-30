package book.infra;

import book.domain.*;
import book.dto.BookDetailDto;
import book.dto.BookSummaryDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository; //

    /**
     * 기능 1: 출판된 모든 책 목록 조회
     * isBookPublished가 true인 책들의 지정된 필드 목록을 반환합니다.
     */
    @GetMapping("/published")
    public ResponseEntity<List<BookSummaryDto>> getPublishedBooks() {
        List<Book> books = bookRepository.findAllByIsBookPublishedTrue();
        List<BookSummaryDto> bookSummaries = books.stream()
                .map(book -> new BookSummaryDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthorId(),
                        book.getIsBestseller(),
                        book.getSummary(),
                        book.getViewCount(),
                        book.getBookCoverUrl(),
                        book.getPrice()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookSummaries);
    }

    /**
     * 기능 2: 베스트셀러 목록 조회
     * isBestseller가 true인 책들의 지정된 필드 목록을 반환합니다.
     */
    @GetMapping("/bestsellers")
    public ResponseEntity<List<BookSummaryDto>> getBestsellerBooks() {
        List<Book> books = bookRepository.findAllByIsBestsellerTrue();
        List<BookSummaryDto> bestsellerSummaries = books.stream()
                .map(book -> new BookSummaryDto(
                        book.getId(),
                        book.getTitle(),
                        book.getAuthorId(),
                        book.getIsBestseller(),
                        book.getSummary(),
                        book.getViewCount(),
                        book.getBookCoverUrl(),
                        book.getPrice()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bestsellerSummaries);
    }


    @GetMapping("/{id}/details")
    public ResponseEntity<?> getBookDetails( // 반환 타입을 와일드카드로 변경
                                             @PathVariable Long id,
                                             @RequestHeader(value = "X-USER-ID", required = true) String userId // 헤더에서 사용자 ID 받기
    ) {
        try {
            // 1. 권한 확인 요청 이벤트 생성
            BookAccessRequested event = new BookAccessRequested();
            event.setId(id);
            event.setPrice(bookRepository.findById(id).map(Book::getPrice).orElse(0)); // 책 가격 설정
            event.setUserId(userId);

            // Saga 라우터에 대기 등록 (userId를 correlationId로 사용)
            EventSagaRouter.V(userId);

            // 2. 이벤트 발행
            event.publish();

            // 3. Saga 라우터를 통해 응답 이벤트 대기
            BookAccessChecked resultEvent = (BookAccessChecked) EventSagaRouter.P(userId);


            // 4. 결과 확인 및 분기 처리
            if (resultEvent != null && resultEvent.isAllowed()) {
                // ... (기존 책 상세 정보 반환 로직) ...
                return bookRepository
                        .findById(id)
                        .map(book -> {
                            book.setViewCount(book.getViewCount() + 1);

                            // save를 통해 @PreUpdate 콜백(베스트셀러 상태 업데이트 로직)이 트리거됩니다.
                            Book updatedBook = bookRepository.save(book); //

                            BookDetailDto bookDetail = new BookDetailDto(
                                    updatedBook.getId(),
                                    updatedBook.getTitle(),
                                    updatedBook.getContent(),
                                    updatedBook.getViewCount(),
                                    updatedBook.getAuthorId(),
                                    updatedBook.getIsBestseller(),
                                    updatedBook.getBookCoverUrl(),
                                    updatedBook.getPrice()
                            );
                            return ResponseEntity.ok(bookDetail);
                        })
                        .orElse(ResponseEntity.notFound().build());
            } else {
                return ResponseEntity.status(403).body("Access Denied");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(503).body("Service unavailable or timeout");
        }
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