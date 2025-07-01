package member.library.controller;

import lombok.RequiredArgsConstructor;
import member.library.service.ExternalService;
import org.springframework.web.bind.annotation.*;
import member.library.domain.MemberInfo;
import member.library.domain.User;
import member.library.domain.UserRepository;
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalService externalService;
    private final UserRepository userRepository;


    @GetMapping("/{id}/author")
    public String getAuthorInfo(@PathVariable Long id) {
        return externalService.callAuthor(id);
    }

    @GetMapping("/{id}/book")
    public String getBookInfo(@PathVariable Long id) {
        return externalService.callBook(id);
    }
    @PostMapping("/test")
    public String testPost(@RequestBody String payload) {
        return "받은 데이터: " + payload;
    }
    @PostMapping
    public String createMember(@RequestBody MemberInfo memberInfo) {
        User user = new User();
        user.setName(memberInfo.getName());
        user.setPhoneNumber(memberInfo.getPhoneNumber());
        User savedUser = userRepository.save(user);
        return "회원 생성 완료: ID=" + savedUser.getId() + ", 이름=" + savedUser.getName();
    }

}
