package member.library.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    private final StreamBridge streamBridge;
    private final String bindingName;

    public EventPublisher(StreamBridge streamBridge, @Value("${event.binding-name:processEvent-out-0}") String bindingName) {
        this.streamBridge = streamBridge;
        this.bindingName = bindingName;
    }

    public void publishEvent(String payload) {
        boolean sent = streamBridge.send(bindingName, payload);
        if (sent) {
            log.info("✅ Event sent successfully to {}: {}", bindingName, payload);
        } else {
            log.error("❌ Failed to send event to {}: {}", bindingName, payload);
        }
    }
}
