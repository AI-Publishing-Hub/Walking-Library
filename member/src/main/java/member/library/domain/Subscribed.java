package member.library.domain;

import java.util.Date;
import java.util.*;
import lombok.Data;
import lombok.ToString;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Subscribed extends AbstractEvent {

    private Long id;
    private String subscripted;
    private Date subscriptionStartAt;
    private Date subscriptionEndAt;

    public Subscribed(User aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.subscriptionStatus = aggregate.getSubscriptionStatus() != null
                ? aggregate.getSubscriptionStatus().name()
                : null;
        this.subscriptionStartAt = aggregate.getSubscriptionStartAt();
        this.subscriptionEndAt = aggregate.getSubscriptionEndAt();
    }

    public Subscribed() {
        super();
    }
}
//>>> DDD / Domain Event