package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

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
