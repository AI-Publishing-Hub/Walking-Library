// PolicyHandler.java

package book.infra;

import book.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

// policy르 위한 것
@Configuration
public class PolicyHandler {

    @Autowired
    BookRepository bookRepository;

    @Bean
    public Consumer<Message<String>> bookEventConsumer() {
        return message -> {
            String eventType = (String) message.getHeaders().get("type");

            System.out.println(
                    "##### [Consumer] 수신된 메시지: " +
                            message.getPayload() +
                            ", 이벤트 타입: " +
                            eventType +
                            " #####"
            );
        };
    }
    @Bean
    public Consumer<Message<BookAccessChecked>> wheneverBookAccessChecked_Route() {
        return message -> {
            BookAccessChecked event = message.getPayload();
            // userId를 correlationId로 사용하여 대기 중인 스레드에 결과를 전달
            EventSagaRouter.E(event.getUserId(), event);
        };
    }


    @Bean
    public Consumer<Message<BookAccessRequested>> wheneverBookAccessRequested_CheckPolicy() {
        return message -> {
            BookAccessRequested event = message.getPayload();
            System.out.println(
                    "\n\n##### [Policy] 책 접근 요청 수신 : " + event + " #####\n\n"
            );

            //--- 비즈니스 로직 (권한 확인 시뮬레이션) ---//

            // 1. 여기서 실제로는 DB나 외부 Member 서비스를 조회해야 합니다.
            // 2. 예제에서는 간단히 userId가 "admin"일 경우에만 허용하도록 시뮬레이션합니다.
//            boolean isAllowed = "admin".equalsIgnoreCase(event.getUserId());
            boolean isAllowed = true; // 테스트 상 허용시킴
            // 3. 확인 결과를 담은 이벤트를 생성합니다.
            BookAccessChecked accessCheckedEvent = new BookAccessChecked();
            accessCheckedEvent.setBookId(event.getId());
            accessCheckedEvent.setUserId(event.getUserId());
            accessCheckedEvent.setAllowed(isAllowed);

            // 4. 이벤트 발행
            accessCheckedEvent.publish();

            System.out.println(
                    "\n\n##### [Policy] 권한 확인 결과 발행 : " +
                            accessCheckedEvent +
                            " #####\n\n"
            );
        };
    }
}