package member.library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import member.MemberApplication;
import member.library.domain.ChargedPoint;
import member.library.domain.PointConsumed;
import member.library.domain.SignedUp;
import member.library.domain.Subscribed;

@Entity
@Table(name = "User_table")
@Data
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    private role role;

    public enum role {
        GENERAL, AUTHOR, ADMIN
    }

    private SubscriptionStatus subscriptionStatus;

    public enum SubscriptionStatus {
        ACTIVE, INACTIVE
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
    public static void consumePoints(BookViewed bookViewed) {
        Long userId = bookViewed.getMemberId();
        Integer price = bookViewed.getPrice();
 
        repository().findById(userId).ifPresent(user -> {
        // 포인트 잔액 확인하기(삼항연산자를 활용하여 확인이 안되면 0으로)
        int currentBalance = user.getPointBalance() != null ? user.getPointBalance() : 0;
 
        if (currentBalance >= price) {
            user.setPointBalance(currentBalance - price);
            user.setUpdatedAt(new Date());
 
            repository().save(user);
 
            PointConsumed pointConsumed = new PointConsumed(user);
            pointConsumed.publishAfterCommit();
        } else {
            throw new RuntimeException("포인트가 부족합니다."); // 잔액이 부족할 경우 예외처리
        }
    });



        //implement business logic here:

        /** Example 1:  new item 
        User user = new User();
        repository().save(user);

        PointConsumed pointConsumed = new PointConsumed(user);
        pointConsumed.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(bookViewed.get???()).ifPresent(user->{
            
            user // do something
            repository().save(user);

            PointConsumed pointConsumed = new PointConsumed(user);
            pointConsumed.publishAfterCommit();

         });
        */
    }
}    
