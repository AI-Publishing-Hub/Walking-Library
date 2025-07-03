package member.library.infra;

import jakarta.transaction.Transactional;
import member.library.domain.User;
import member.library.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    // --- 이 부분을 새로 추가해주세요 ---
    /**
     * 신규 회원 가입 API
     */
    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody User user) {
        // 전달받은 user 객체의 기본값을 설정하고 저장
        // (실제로는 Request DTO를 따로 만드는 것이 더 좋습니다)
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // @PostMapping("/{id}/charge")
    // public String chargePoints(@PathVariable Long id, @RequestBody ChargedPoint chargedPoint) {
    //     int amount = chargedPoint.getChargedAmount();
    //     User user = new User();
    //     user = userRepository.findById(id);
    //     user.chargePoint(amount);
    //     userRepository.save(user);
        
    //     return "충전 완료";
    // }

    // @PostMapping("/{id}/subscribe")
    // public String subscribe(@PathVariable Long id, @RequestBody Subscribed subscribed) {

    //     int days = 30;

    //     user.subscribe(days);
    //     userRepository.save(user);

    //     return "구독 완료";
    }
}