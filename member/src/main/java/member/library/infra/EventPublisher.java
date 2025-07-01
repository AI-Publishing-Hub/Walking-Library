package member.library.infra;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final StreamBridge streamBridge;

    public EventPublisher(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishEvent(String payload) {
        // processEvent-out-0는 application.yml에 설정한 바인딩명과 일치해야 함
        streamBridge.send("processEvent-out-0", payload);
    }
}
