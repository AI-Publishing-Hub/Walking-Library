package member.library.domain;

import java.time.LocalDate;
import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

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

    private final UserRepository userRepository;

    public SignedUp(User aggregate) {
        super(aggregate);

        User user = new User();
        user.setName(user.getName());
        user.setPhoneNumber(user.getPhoneNumber());

        User savedUser = userRepository.save(user);

        SignedUp event = new SignedUp(savedUser);
        event.publishAfterCommit(); 
        return savedUser;
    }

    public SignedUp() {
        super();
    }
}