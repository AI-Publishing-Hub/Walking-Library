package book.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

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

    @Lob
    private String summary;

    private Long bookReleaseAdminId;

    private Long authorId;

    private Boolean isBestseller;
}
