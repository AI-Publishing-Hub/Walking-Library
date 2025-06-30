package book.domain;

import book.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookAccessRequested extends AbstractEvent {

    private Long id; // Book ID
    private String userId; // 권한을 확인할 사용자 ID
    private Integer price; // 책 가격

    public BookAccessRequested() {
        super();
    }
}