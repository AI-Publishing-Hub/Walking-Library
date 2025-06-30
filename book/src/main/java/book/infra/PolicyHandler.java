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
}