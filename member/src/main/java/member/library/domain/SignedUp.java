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
public class SignedUp extends AbstractEvent {

    private Long id;
    private String name;
    private String phoneNumber;
    private Integer pointBalance;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isKtVerified;

    public SignedUp(User aggregate) {
        super(aggregate);
    }

    public SignedUp() {
        super();
    }
}