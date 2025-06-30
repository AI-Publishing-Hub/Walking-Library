package book.domain;

import book.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BestsellerStatusChanged extends AbstractEvent {

    private Long id;
    private String title;
    private Long viewCount;
    private Boolean isBestseller;

    public BestsellerStatusChanged(Book aggregate) {
        super(aggregate);
    }

    public BestsellerStatusChanged() {
        super();
    }
}