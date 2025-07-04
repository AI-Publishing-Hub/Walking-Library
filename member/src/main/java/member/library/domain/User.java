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
    // // // 구독로직
    // // // 구독 기간 시작
    // public void subscribe(int days) {
    //     this.subscriptionStatus = SubscriptionStatus.ACTIVE;
    //     this.subscriptionStartAt = new Date();
    //     this.subscriptionEndAt = Date.from(this.subscriptionStartAt.toInstant().plus(Duration.ofDays(days)));
    //     this.updatedAt = new Date();
    // }

    // // // 구독 기간 끝
    // public void unsubscribe() {
    //     this.subscriptionStatus = SubscriptionStatus.INACTIVE;
    //     this.subscriptionEndAt = new Date();
    //     this.updatedAt = new Date();
    // }

    // // //포인트 충전 로직
    // public void chargePoint(int amount) {
    // if (this.pointBalance == null) this.pointBalance = 0;
    // this.pointBalance += amount;
    // this.updatedAt = new Date();
    // }

    //포인트 소모 로직
    public static void consumePoints(BookViewed bookViewed) {
        Long userId = bookViewed.getMemberId();
        Integer price = bookViewed.getPrice();
        
        //로직 수정1
        // User user = userRepository.findById(userId);
        // int currentBalance = user.getPointBalance() != null ? user.getPointBalance() : 0;

        // if (currentBalance >= price) {
        //     user.setUpdatedAt(new Date());
        //     user.setPointBalance(currentBalance - price);
 
        //     repository().save(user);
 
        //     PointConsumed pointConsumed = new PointConsumed(user);
        //     pointConsumed.publishAfterCommit();
        // } else {
        //     throw new RuntimeException("포인트가 부족합니다."); // 잔액이 부족할 경우 예외처리
        // }

        //로직 수정 전
        // userRepository().findById(Id).ifPresent(user -> {
        //  포인트 잔액 확인하기(삼항연산자를 활용하여 확인이 안되면 0으로)
        // int currentBalance = user.getPointBalance() != null ? user.getPointBalance() : 0;
 
        // if (currentBalance >= price) {
        //     user.setUpdatedAt(new Date());
        //     user.setPointBalance(currentBalance - price);
 
        //     repository().save(user);
 
        //     PointConsumed pointConsumed = new PointConsumed(user);
        //     pointConsumed.publishAfterCommit();
        // } else {
        //     throw new RuntimeException("포인트가 부족합니다."); // 잔액이 부족할 경우 예외처리
        // }
        // });

        
            

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
