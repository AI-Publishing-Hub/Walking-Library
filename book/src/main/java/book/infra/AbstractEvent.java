package book.infra;

import book.BookApplication;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;


@Setter
@Getter
public abstract class AbstractEvent {

    String eventType;
    Long timestamp;

    public AbstractEvent(Object aggregate) {
        this();
        BeanUtils.copyProperties(aggregate, this);
    }

    public AbstractEvent() {
        this.setEventType(this.getClass().getSimpleName());
        this.timestamp = System.currentTimeMillis();
    }

    public void publish() {
        // StreamBridge를 ApplicationContext에서 가져옵니다.
        StreamBridge streamBridge = BookApplication.applicationContext.getBean(
                StreamBridge.class
        );

        Message<AbstractEvent> message = MessageBuilder
                .withPayload(this)
                .setHeader(
                        MessageHeaders.CONTENT_TYPE,
                        MimeTypeUtils.APPLICATION_JSON_VALUE
                )
                .setHeader("type", getEventType())
                .build();

        // yml에 정의된 프로듀서 바인딩 이름으로 메시지를 발행합니다.
        streamBridge.send("bookEventProducer-out-0", message);
    }

    public void publishAfterCommit() {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() { // <- Adapter를 인터페이스 직접 구현으로 변경
                    @Override
                    public void afterCompletion(int status) {
                        if (status == TransactionSynchronization.STATUS_COMMITTED) {
                            AbstractEvent.this.publish();
                        }
                    }
                }
        );
    }

    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }
}