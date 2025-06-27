package library.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MemberInfo_table")
@Data
public class MemberInfo {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private String role;
    private String subscriptionStatus;
    private Integer pointBalance;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isKtVerified;
}
