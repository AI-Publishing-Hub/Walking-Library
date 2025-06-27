package member.src.main.java.library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Inherited;
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

public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String password;

    private String phoneNumber;

    private role role;

    private SubscriptionStatus subscriptionStatus;

    private Date subscriptionStartAt;

    private Date subscriptionEndAt;

    private Integer pointBalance;

    private Date createdAt;

    private Date updatedAt;

    private Boolean isKtVerified;
}