package member.library.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String callAuthor(Long id) {
        // author 컨텍스트의 엔드포인트 호출 예제
        String authorServiceUrl = "http://author:8080/api/authors/" + id;
        return restTemplate.getForObject(authorServiceUrl, String.class);
    }

    public String callBook(Long id) {
        // book 컨텍스트의 엔드포인트 호출 예제
        String bookServiceUrl = "http://book:8080/api/books/" + id;
        return restTemplate.getForObject(bookServiceUrl, String.class);
    }
}
