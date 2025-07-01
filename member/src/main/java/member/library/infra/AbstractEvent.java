package member.library.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.Transient;
import member.MemberApplication;
//import member.library.config.KafkaProcessor;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;



//<<< Clean Arch / Outbound Adaptor
public class AbstractEvent {

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
        /**
         * spring streams 방식
         */
        // KafkaProcessor processor = MemberApplication.applicationContext.getBean(
        //     KafkaProcessor.class
        // );
        // MessageChannel outputChannel = processor.outboundTopic();

        // outputChannel.send(
        //     MessageBuilder
        //         .withPayload(this)
        //         .setHeader(
        //             MessageHeaders.CONTENT_TYPE,
        //             MimeTypeUtils.APPLICATION_JSON
        //         )
        //         .setHeader("type", getEventType())
        //         .build()
        // );
    }
    @Autowired
    @Transient
    private EventPublisher eventPublisher;

    public void publishAfterCommit() {
        String jsonPayload = serializeEvent(this);
        eventPublisher.publishEvent(jsonPayload);
    }
    private String serializeEvent(Object event) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException("Event serialization failed", e);
        }
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        return json;
    }
}
//>>> Clean Arch / Outbound Adaptor
