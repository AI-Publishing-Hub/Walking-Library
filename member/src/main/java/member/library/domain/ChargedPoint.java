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
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다"));

        int updatedBalance = user.getPointBalance() + amount;
        user.setPointBalance(updatedBalance);
        userRepository.save(user);
    }

    public ChargedPoint() {
        super();
    }

}
//>>> DDD / Domain Event
