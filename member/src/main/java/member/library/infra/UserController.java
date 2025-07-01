package member.library.infra;

import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import member.library.domain.*;
import member.library.domain.User.SignUpRequest;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody SignUpRequest request) {
        User user = userService.signup(request);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/charge")
    public ResponseEntity<String> chargePoint(
        @PathVariable Long id,
        @RequestBody PointChargeRequest request
    ) {
    userService.chargePoint(id, request.getAmount());
    return ResponseEntity.ok("충전이 완료되었습니다.");
    }
    
}
//>>> Clean Arch / Inbound Adaptor
