package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointConsumed extends AbstractEvent {

    private Long id;
    private Integer pointBalance;

    public PointConsumed(User aggregate) {
        super(aggregate);
    }

    public PointConsumed() {
        super();
    }
}
//>>> DDD / Domain Event
