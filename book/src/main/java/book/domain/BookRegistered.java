package book.domain;

import book.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookRegistered extends AbstractEvent {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isBookPublished;
    private Integer price;
    private String bookCoverUrl;
    private Long authorId;
    private String summary;

    public BookRegistered(Book aggregate) {
        super(aggregate);
    }

    public BookRegistered() {
        super();
    }
}