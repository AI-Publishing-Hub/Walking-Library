package member.library.controller;

import lombok.RequiredArgsConstructor;
import member.library.service.ExternalService;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.Token.ID;

import member.library.domain.MemberInfo;
import member.library.domain.User;
import member.library.domain.UserRepository;
import member.library.domain.User.SubscriptionStatus;
import member.library.domain.User.role;
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
        //user.getId()(ID.valueOf(memberInfo.getId()));
        user.setName(memberInfo.getName());
        user.setPhoneNumber(memberInfo.getPhoneNumber());
        user.setId(memberInfo.getId());
        user.setPointBalance(memberInfo.getPointBalance());
        user.setSubscriptionStatus(SubscriptionStatus.valueOf(memberInfo.getSubscriptionStatus()));
        user.setRole(role.valueOf(memberInfo.getRole()));
        user.setCreatedAt(memberInfo.getCreatedAt());
        user.setUpdatedAt(memberInfo.getUpdatedAt());
        user.setIsKtVerified(memberInfo.getIsKtVerified());

        
        User savedUser = userRepository.save(user);
        return "회원 생성 완료: ID=" + savedUser.getId() + ", 이름=" + savedUser.getName();
    }

}
