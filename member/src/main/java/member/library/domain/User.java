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
import member.MemberApplication;
import member.library.domain.ChargedPoint;
import member.library.domain.PointConsumed;
import member.library.domain.SignedUp;
import member.library.domain.Subscribed;

@Entity
@Table(name = "User_table")
@Data

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    private role role;

    public enum role {
    
}

    private SubscriptionStatus subscriptionStatus;

    public enum SubscriptionStatus {

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
