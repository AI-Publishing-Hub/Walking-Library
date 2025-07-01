package member.library.controller;

import lombok.RequiredArgsConstructor;
import member.library.service.ExternalService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ExternalController {

    private final ExternalService externalService;

    @GetMapping("/{id}/author")
    public String getAuthorInfo(@PathVariable Long id) {
        return externalService.callAuthor(id);
    }

    @GetMapping("/{id}/book")
    public String getBookInfo(@PathVariable Long id) {
        return externalService.callBook(id);
    }
}
