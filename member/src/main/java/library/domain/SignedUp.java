package library.domain;

import java.time.LocalDate;
import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
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