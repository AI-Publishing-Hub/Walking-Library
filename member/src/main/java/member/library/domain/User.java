package member.library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Inherited;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
        USER, ADMIN
}

    private SubscriptionStatus subscriptionStatus;

    public enum SubscriptionStatus {
        SUBSCRIPTED, UNSUBSCRIPTED
}

    private Date subscriptionStartAt;

    private Date subscriptionEndAt;

    private Integer pointBalance;

    private Date createdAt;

    private Date updatedAt;

    private Boolean isKtVerified;


    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class SignUpRequest {
        private String name;
        private String phoneNumber;
    }

    
}



