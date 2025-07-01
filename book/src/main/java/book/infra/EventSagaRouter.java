package book.infra;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class EventSagaRouter {

    private static final Map<String, CountDownLatch> latches = new ConcurrentHashMap<>();
    private static final Map<String, Object> results = new ConcurrentHashMap<>();

    // 요청 시작 시 호출
    public static void V(String correlationId) {
        latches.put(correlationId, new CountDownLatch(1));
    }

    // 응답을 기다림
    public static Object P(String correlationId)
            throws InterruptedException {
        CountDownLatch latch = latches.get(correlationId);
        if (latch != null && latch.await(5, TimeUnit.SECONDS)) { // 최대 5초 대기
            return results.remove(correlationId);
        }
        latches.remove(correlationId);
        throw new InterruptedException("Timeout or no latch found for correlationId: " + correlationId);
    }

    // 응답 이벤트 수신 시 호출
    public static void E(String correlationId, Object result) {
        results.put(correlationId, result);
        CountDownLatch latch = latches.get(correlationId);
        if (latch != null) {
            latch.countDown();
        }
        latches.remove(correlationId);
    }
}