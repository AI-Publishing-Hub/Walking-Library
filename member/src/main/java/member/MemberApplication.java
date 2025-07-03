package member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan; // <-- 임포트 추가

@SpringBootApplication
// @ComponentScan(basePackages = {"member.library", "member"}) // ★ 이 줄을 추가해주세요!
public class MemberApplication {
    public static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(MemberApplication.class, args);
    }
}