package book.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;
@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public AiResult analyzeBook( BookRegistered bookRegistered) {
        String title = bookRegistered.getTitle();
        String content = bookRegistered.getContent();
        String prompt = String.format("""
            글 제목: %s
            본문: %s

            이 글에 대해 다음을 생성해줘:
            1. 2~3문장 요약
            2. 적절한 1~5만원 사이의 책 가격 (정수로)
            3. 글 제목과 본문을 기반으로 한 이미지 생성 프롬프트 (예: "A fantasy book cover with dragons and fire")
            """, title, content);

        // GPT 요청 payload
        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4",
                "messages", List.of(Map.of(
                        "role", "user",
                        "content", prompt
                )),
                "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_URL, request, Map.class);

        Map<String, Object> body = response.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, String> message = (Map<String, String>) firstChoice.get("message");
        String resultText = message.get("content");

        // 예: GPT가 생성한 내용 (기본 텍스트 기반 파싱)
        String[] lines = resultText.split("\n");

        String summary = lines[0].replace("1.", "").trim();
        int price = Integer.parseInt(lines[1].replaceAll("[^0-9]", ""));
        String imagePrompt = lines[2].replace("3.", "").trim();

// 이미지 생성
        String imageUrl = generateImage(imagePrompt);

        return new AiResult(summary, price, imageUrl);
    }

    public String generateImage(String prompt) {
        String url = "https://api.openai.com/v1/images/generations";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);  // 같은 OpenAI 키 사용

        Map<String, Object> body = Map.of(
                "prompt", prompt,
                "n", 1,
                "size", "512x512"
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Map<String, String>> data = (List<Map<String, String>>) response.getBody().get("data");
        return data.get(0).get("url");
    }


    public record AiResult(String summary, int price, String bookCoverUrl) {}
}