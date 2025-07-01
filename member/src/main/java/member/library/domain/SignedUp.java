package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import lombok.Getter;
import lombok.Setter;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@Getter
@Setter
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
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.phoneNumber = aggregate.getPhoneNumber();
        this.pointBalance = aggregate.getPointBalance();
        this.createdAt = aggregate.getCreatedAt();
        this.updatedAt = aggregate.getUpdatedAt();
        this.isKtVerified = aggregate.getIsKtVerified();
    }

    public SignedUp() {
        super();
    }
}