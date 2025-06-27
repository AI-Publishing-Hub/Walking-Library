package book.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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

    private Long authorId;

    private Boolean isBestseller;
}
