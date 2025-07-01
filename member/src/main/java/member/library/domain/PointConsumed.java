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

    public PointConsumed(Long Id, int pointBalance) {
        super(aggregate);

        User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getPointBalance() < amount) {
        throw new IllegalArgumentException("포인트가 부족합니다.");
        }

        user.setPointBalance(user.getPointBalance() - amount);
        userRepository.save(user);
    }
    

    public PointConsumed() {
        super();
    }
}
//>>> DDD / Domain Event
