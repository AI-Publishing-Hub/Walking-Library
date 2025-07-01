package member.library.infra;

import member.library.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class MemberInfoViewHandler {

    private static final Logger log = LoggerFactory.getLogger(MemberInfoViewHandler.class);

    private final MemberInfoRepository memberInfoRepository;

    public MemberInfoViewHandler(MemberInfoRepository memberInfoRepository) {
        this.memberInfoRepository = memberInfoRepository;
    }

    // 회원가입 이벤트: MemberInfo 생성
    @Bean
    public Consumer<SignedUp> whenSignedUp() {
        return signedUp -> {
            try {
                if (!signedUp.validate()) return;

                MemberInfo memberInfo = new MemberInfo(
                    signedUp.getId(),
                    signedUp.getName(),
                    signedUp.getPhoneNumber(),
                    "USER",
                    "UNSUBSCRIBED",
                    signedUp.getPointBalance(),
                    signedUp.getCreatedAt(),
                    signedUp.getUpdatedAt(),
                    signedUp.getIsKtVerified()
                );

                memberInfoRepository.save(memberInfo);
                log.info("✅ MemberInfo created: {}", memberInfo);

            } catch (Exception e) {
                log.error("❌ Failed to handle SignedUp event", e);
            }
        };
    }

    // 포인트 차감 이벤트: pointBalance 업데이트
    @Bean
    public Consumer<PointConsumed> whenPointConsumed() {
        return event -> {
            try {
                if (!event.validate()) return;

                memberInfoRepository.findById(event.getId()).ifPresent(memberInfo -> {
                    memberInfo.setPointBalance(event.getPointBalance());
                    memberInfoRepository.save(memberInfo);
                    log.info("✅ MemberInfo updated (point consumed): {}", memberInfo);
                });

            } catch (Exception e) {
                log.error("❌ Failed to handle PointConsumed event", e);
            }
        };
    }

    // 포인트 충전 이벤트: pointBalance 업데이트
    @Bean
    public Consumer<ChargedPoint> whenChargedPoint() {
        return event -> {
            try {
                if (!event.validate()) return;

                memberInfoRepository.findById(event.getId()).ifPresent(memberInfo -> {
                    memberInfo.setPointBalance(event.getPointBalance());
                    memberInfoRepository.save(memberInfo);
                    log.info("✅ MemberInfo updated (point charged): {}", memberInfo);
                });

            } catch (Exception e) {
                log.error("❌ Failed to handle ChargedPoint event", e);
            }
        };
    }

    // 구독 상태 변경 이벤트: subscriptionStatus, 시작/종료일 업데이트
    @Bean
    public Consumer<Subscribed> whenSubscribed() {
        return event -> {
            try {
                if (!event.validate()) return;

                memberInfoRepository.findById(event.getId()).ifPresent(memberInfo -> {
                    memberInfo.setSubscriptionStatus(event.getSubscriptionStatus());
                    memberInfo.setUpdatedAt(event.getTimestamp() != null
                            ? new java.util.Date(event.getTimestamp())
                            : new java.util.Date());
                    memberInfoRepository.save(memberInfo);
                    log.info("✅ MemberInfo updated (subscription): {}", memberInfo);
                });

            } catch (Exception e) {
                log.error("❌ Failed to handle Subscribed event", e);
            }
        };
    }
}
