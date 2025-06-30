package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ChargedPoint extends AbstractEvent {

    private Long id;
    private Integer pointBalance;

    public ChargedPoint(User aggregate) {
        super(aggregate);
    }

    public ChargedPoint() {
        super();
    }
}
//>>> DDD / Domain Event
