package book.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "BookView_table")
@Data
@NoArgsConstructor
public class BookView {

    @Id
    private Long id;
    private String title;

    @Lob
    private String content;

    private Long viewCount;
    private Long authorId;
    private Boolean isBestseller;
    private String bookCoverUrl;
    private Integer price;
    private Boolean isBookPublished;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Lob
    private String summary;
}