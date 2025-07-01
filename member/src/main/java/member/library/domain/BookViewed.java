package member.library.domain;

import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Integer price;
    private Long bookId;
    private Date viewedAt;

    public Long getUserId() {
    return memberId;
}

}


@Builder
public BookViewed(Long id, Long memberId, Integer price) {
    super();
    this.id = id;
    this.memberId = memberId;
    this.price = price;
    this.bookId = bookId;
    this.viewedAt = viewedAt;
}