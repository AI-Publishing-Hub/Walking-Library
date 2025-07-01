package member.library.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import member.MemberApplication;
import org.springframework.beans.BeanUtils;

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

    /**
     * [수정된 부분]
     * EventPublisher를 직접 필드로 갖는 대신,
     * 필요할 때마다 Spring의 ApplicationContext에서 직접 가져와서 사용합니다.
     */
    public void publishAfterCommit() {
        // ApplicationContext에서 EventPublisher Bean을 찾아옴
        EventPublisher eventPublisher = MemberApplication.applicationContext.getBean(
                EventPublisher.class
        );
        // 찾아온 Bean을 사용해서 이벤트 발행
        eventPublisher.publishEvent(this.toJson());
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
}