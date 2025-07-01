package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Subscribed extends AbstractEvent {

    private Long id;
    private String subscripted;

    public Subscribed(User aggregate) {
        super(aggregate);
    }

    public Subscribed() {
        super();
    }
}
//>>> DDD / Domain Event