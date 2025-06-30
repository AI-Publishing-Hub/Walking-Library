// PolicyHandler.java

package book.infra;

import book.domain.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired
    private BookViewHandler bookViewHandler;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Consumer<Message<String>> bookEventConsumer() {
        return message -> {
            String eventType = (String) message.getHeaders().get("type");
            System.out.println("##### 수신된 이벤트 타입 : " + eventType + " / 페이로드 : " + message.getPayload() + " #####");

            try {
                // 주입받은 objectMapper를 사용하여 역직렬화를 수행합니다.
                if ("BookRegistered".equals(eventType)) {
                    bookViewHandler.whenBookRegistered_then_createView(
                            objectMapper.readValue(message.getPayload(), BookRegistered.class)
                    );
                } else if ("BestsellerStatusChanged".equals(eventType)) {
                    bookViewHandler.whenBestsellerStatusChanged_then_updateView(
                            objectMapper.readValue(message.getPayload(), BestsellerStatusChanged.class)
                    );
                }
            } catch (Exception e) {
                // 예외 발생 시 로그를 남겨 문제를 파악하기 쉽게 합니다.
                System.err.println("##### [에러] 이벤트 처리 중 예외 발생 : " + eventType + " #####");
                e.printStackTrace();
            }
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

//    @Bean
//    public Consumer<Message<String>> wheneverBookAccessRequested_CheckPolicy() { // 타입을 String으로 받도록 변경
//        return message -> {
//            // 헤더 'type'을 먼저 확인해서 우리가 원하는 이벤트가 맞는지 직접 확인합니다.
//            String eventType = (String) message.getHeaders().get("type");
//            if ("BookAccessRequested".equals(eventType)) {
//                System.out.println(
//                        "\n\n##### [Debug] 'BookAccessRequested' 타입 메시지 수신! 이제 역직렬화를 시도합니다. #####\n\n"
//                );
//                try {
//                    // 수동으로 역직렬화를 시도합니다.
//                    BookAccessRequested event = objectMapper.readValue(message.getPayload(), BookAccessRequested.class);
//
//                    // 성공 시, 기존 로직을 그대로 수행합니다.
//                    System.out.println(
//                            "\n\n##### [Policy] 책 접근 요청 수신 : " + event + " #####\n\n"
//                    );
//
//                    // --- 기존 비즈니스 로직 --- //
//                    boolean isAllowed = true;
//                    BookAccessChecked accessCheckedEvent = new BookAccessChecked();
//                    accessCheckedEvent.setBookId(event.getId());
//                    accessCheckedEvent.setUserId(event.getUserId());
//                    accessCheckedEvent.setAllowed(isAllowed);
//                    accessCheckedEvent.publish();
//
//                    System.out.println(
//                            "\n\n##### [Policy] 권한 확인 결과 발행 : " +
//                                    accessCheckedEvent +
//                                    " #####\n\n"
//                    );
//
//                } catch (Exception e) {
//                    // 역직렬화 실패 시, 에러를 콘솔에 모두 출력합니다.
//                    System.err.println("\n\n##### [Debug] 역직렬화 중 심각한 오류 발생! 원인은 다음과 같습니다. #####");
//                    e.printStackTrace();
//                }
//            }
//        };
//    }
//    @Bean
//    public Consumer<Message<BookAccessRequested>> wheneverBookAccessRequested_CheckPolicy() {
//        return message -> {
//            BookAccessRequested event = message.getPayload();
//            System.out.println(
//                    "\n\n##### [Policy] 책 접근 요청 수신 : " + event + " #####\n\n"
//            );
//
//            //--- 비즈니스 로직 (권한 확인 시뮬레이션) ---//
//
//            // 1. 여기서 실제로는 DB나 외부 Member 서비스를 조회해야 합니다.
//            // 2. 예제에서는 간단히 userId가 "admin"일 경우에만 허용하도록 시뮬레이션합니다.
////            boolean isAllowed = "admin".equalsIgnoreCase(event.getUserId());
//            boolean isAllowed = true; // 테스트 상 허용시킴
//            // 3. 확인 결과를 담은 이벤트를 생성합니다.
//            BookAccessChecked accessCheckedEvent = new BookAccessChecked();
//            accessCheckedEvent.setBookId(event.getId());
//            accessCheckedEvent.setUserId(event.getUserId());
//            accessCheckedEvent.setAllowed(isAllowed);
//
//            // 4. 이벤트 발행
//            accessCheckedEvent.publish();
//
//            System.out.println(
//                    "\n\n##### [Policy] 권한 확인 결과 발행 : " +
//                            accessCheckedEvent +
//                            " #####\n\n"
//            );
//        };
//    }
}