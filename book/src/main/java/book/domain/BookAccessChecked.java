package book.domain;

import book.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookAccessChecked extends AbstractEvent {

    private Long bookId;
    private String userId;
    private boolean isAllowed; // 허용 여부

    public BookAccessChecked() {
        super();
    }
}