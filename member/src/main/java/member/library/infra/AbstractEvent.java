package member.library.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.Transient;
import member.MemberApplication;

import org.springframework.beans.BeanUtils;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
//<<< Clean Arch / Outbound Adaptor
public class AbstractEvent {

    private String eventType;
    private Long timestamp;

    @Transient
    public AbstractEvent(Object aggregate) {
        this();
       
    }

    public AbstractEvent() {
        this.eventType = getClass().getSimpleName();
        this.timestamp = System.currentTimeMillis();
    }

    public void publishAfterCommit() {
        Assert.notNull(eventPublisher, "EventPublisher must not be null");
        String jsonPayload = serializeEvent(this);
        eventPublisher.publishEvent(jsonPayload);
    }
    protected String serializeEvent(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Event serialization failed for type: " + getEventType(), e);
        }
    }

    public boolean validate() {
        return getEventType().equals(getClass().getSimpleName());
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception for type: " + getEventType(), e);
        }
    }

    
}
//>>> Clean Arch / Outbound Adaptor
