package book.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Lob
    private String summary;
}