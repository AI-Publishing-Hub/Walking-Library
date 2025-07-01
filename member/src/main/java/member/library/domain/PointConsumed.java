package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Getter
@Setter
@ToString
public class PointConsumed extends AbstractEvent {

    private Long id;
    private Integer pointBalance;
    private Integer consumedAmount;
    private Date consumedAt;

    public PointConsumed(User aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.pointBalance = aggregate.getPointBalance();
        this.consumedAmount = consumedAmount;
        this.consumedAt = new Date();
    }

    public PointConsumed() {
        super();
    }
}
//>>> DDD / Domain Event
