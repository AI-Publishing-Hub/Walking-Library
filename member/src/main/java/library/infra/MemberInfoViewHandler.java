package library.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import library.config.kafka.KafkaProcessor;
import library.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class MemberInfoViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private MemberInfoRepository memberInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSignedUp_then_CREATE_1(@Payload SignedUp signedUp) {
        try {
            if (!signedUp.validate()) return;

            // view 객체 생성
            MemberInfo memberInfo = new MemberInfo();
            // view 객체에 이벤트의 Value 를 set 함
            memberInfo.setId(signedUp.getId());
            memberInfo.setName(signedUp.getName());
            memberInfo.setRole(USER);
            memberInfo.setSubscriptionStatus(UNSUBSCRIPTED);
            memberInfo.setPointBalance(signedUp.getPointBalance());
            memberInfo.setCreatedAt(signedUp.getCreatedAt());
            memberInfo.setUpdatedAt(signedUp.getUpdatedAt());
            memberInfo.setIsKtVerified(signedUp.getIsKtVerified());
            // view 레파지 토리에 save
            memberInfoRepository.save(memberInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPointConsumed_then_UPDATE_1(
        @Payload PointConsumed pointConsumed
    ) {
        try {
            if (!pointConsumed.validate()) return;
            // view 객체 조회

            List<MemberInfo> memberInfoList = memberInfoRepository.findByPointBalance(
                pointConsumed.getPointBalance()
            );
            for (MemberInfo memberInfo : memberInfoList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberInfo.setId(pointConsumed.getId());
                // view 레파지 토리에 save
                memberInfoRepository.save(memberInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenChargedPoint_then_UPDATE_2(
        @Payload ChargedPoint chargedPoint
    ) {
        try {
            if (!chargedPoint.validate()) return;
            // view 객체 조회

            List<MemberInfo> memberInfoList = memberInfoRepository.findByPointBalance(
                chargedPoint.getPointBalance()
            );
            for (MemberInfo memberInfo : memberInfoList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberInfo.setId(chargedPoint.getId());
                // view 레파지 토리에 save
                memberInfoRepository.save(memberInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscribed_then_UPDATE_3(@Payload Subscribed subscribed) {
        try {
            if (!subscribed.validate()) return;
            // view 객체 조회

            List<MemberInfo> memberInfoList = memberInfoRepository.findBySubscriptionStatus(
                subscribed.getSubscripted()
            );
            for (MemberInfo memberInfo : memberInfoList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                memberInfo.setId(subscribed.getId());
                // view 레파지 토리에 save
                memberInfoRepository.save(memberInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
