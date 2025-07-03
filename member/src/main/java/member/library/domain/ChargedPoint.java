package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ChargedPoint extends AbstractEvent {

    private Long id;
    private Integer pointBalance;

    public ChargedPoint(User aggregate, int amount) {
        super(aggregate);
        this.id = aggregate.getId();
        this.chargedAmount = amount;
        this.pointBalance = aggregate.getPointBalance();
    }

    public ChargedPoint() {
        super();
    }
}
//>>> DDD / Domain Event
