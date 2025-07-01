package member;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.cloud.stream.kafka.binder.brokers=localhost:9099",  // 존재하지 않는 브로커
    "spring.cloud.stream.defaultBinder=mock"                    // 또는 실제 모킹 설정 사용
})
class MemberApplicationTests {

	@Test
	void contextLoads() {
	}

}
