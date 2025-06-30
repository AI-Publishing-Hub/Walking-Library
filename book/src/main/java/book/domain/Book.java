package book.domain;

import book.BookApplication;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long viewCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isBookPublished;

    private Integer price;

    private String bookCoverUrl;
    private Long authorId;

    private Boolean isBestseller;

    @Lob
    private String summary;


    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.viewCount = 0L;
        if (this.isBestseller == null) {
            this.isBestseller = false; // 기본값 설정
        }
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();

        // 베스트셀러 상태 업데이트 로직
        boolean wasBestseller = this.isBestseller != null && this.isBestseller;
        if (!wasBestseller && this.viewCount != null && this.viewCount >= 30) {
            this.isBestseller = true;
            // 베스트셀러 상태 변경 이벤트 발행
            BestsellerStatusChanged bestsellerStatusChanged = new BestsellerStatusChanged(this);
            bestsellerStatusChanged.publishAfterCommit();
        }
    }

    @PostPersist
    public void onPostPersist() {
        BookRegistered bookRegistered = new BookRegistered(this);
        bookRegistered.publishAfterCommit();
    }

    public static BookRepository repository() {
        return BookApplication.applicationContext.getBean(BookRepository.class);
    }
}