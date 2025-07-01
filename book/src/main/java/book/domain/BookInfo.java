package book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class BookInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Long viewCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer price;

    @Column(length = 1024)
    private String bookCoverUrl;

    @Lob
    private String summary;

    private Long authorId;

    private Boolean isBestSeller;
}
