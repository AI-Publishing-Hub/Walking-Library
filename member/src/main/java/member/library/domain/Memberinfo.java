package member.library.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

import lombok.Getter;
//<<< EDA / CQRS
@Entity
@Table(
    name = "MemberInfo_table",
    indexes = {
        @Index(name = "idx_phoneNumber", columnList = "phoneNumber"),
        @Index(name = "idx_subscriptionStatus", columnList = "subscriptionStatus")
    }
)
@Getter
public class MemberInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private String phoneNumber;
    private String role;
    private String subscriptionStatus;
    private Integer pointBalance;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isKtVerified;

    protected MemberInfo() {
        this.id = null;
        this.name = null;
        this.phoneNumber = null;
        this.role = null;
        this.subscriptionStatus = null;
        this.pointBalance = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.isKtVerified = null;
    }

    public MemberInfo(Long id, String name, String phoneNumber, String role,
                      String subscriptionStatus, Integer pointBalance,
                      Date createdAt, Date updatedAt, Boolean isKtVerified) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.subscriptionStatus = subscriptionStatus;
        this.pointBalance = pointBalance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isKtVerified = isKtVerified;
}
