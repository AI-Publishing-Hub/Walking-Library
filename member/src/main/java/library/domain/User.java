package library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import library.MemberApplication;
import library.domain.ChargedPoint;
import library.domain.PointConsumed;
import library.domain.SignedUp;
import library.domain.Subscribed;
import lombok.Data;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    private Role role;

    private SubscriptionStatus subscriptionStatus;

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

    //<<< Clean Arch / Port Method
    public static void consumePoints(BookViewed bookViewed) {
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
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root