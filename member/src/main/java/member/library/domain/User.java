package member.library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.persistence.*;

import lombok.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import member.MemberApplication;
import member.library.domain.ChargedPoint;
import member.library.domain.PointConsumed;
import member.library.domain.SignedUp;
import member.library.domain.Subscribed;

@Entity
@Table(name = "User_table")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;
    private String phoneNumber;

    private role role;

    public enum role {
        USER, ADMIN
}

    private SubscriptionStatus subscriptionStatus;

    public enum SubscriptionStatus {
        NONE, ACTIVE, EXPIRED
}

    private Date subscriptionStartAt;

    private Date subscriptionEndAt;

    private Integer pointBalance;

    private Date createdAt;

    private Date updatedAt;

    private Boolean isKtVerified;

    @PostPersist
    public void onPostPersist() {
        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    public static UserRepository repository() {
        UserRepository userRepository = MemberApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public static void consumePoints(BookViewed bookViewed) {
        Long userId = bookViewed.getUserId(); // BookViewed 클래스에 getUserId()가 있어야 함

    repository().findById(userId).ifPresent(user -> {
        // 포인트 차감 로직
        int pointsToConsume = 10; // 예: 책 열람 시 10포인트 차감
        if (user.getPointBalance() != null && user.getPointBalance() >= pointsToConsume) {
            user.setPointBalance(user.getPointBalance() - pointsToConsume);
        } else {
            throw new IllegalStateException("포인트가 부족합니다.");
        }

        // 변경된 상태 저장
        repository().save(user);

        // 포인트 차감 이벤트 발행
        PointConsumed pointConsumed = new PointConsumed(user);
        pointConsumed.publishAfterCommit();
    });
       
    }
}    
