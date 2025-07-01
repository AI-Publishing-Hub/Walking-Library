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

//        String summary = lines[0].replace("1.", "").trim();
//        int price = Integer.parseInt(lines[1].replaceAll("[^0-9]", ""));
//        String imagePrompt = lines[2].replace("3.", "").trim();

        String summary = "요약 정보를 생성하지 못했습니다.";
        int price = 20000; // 기본 가격
        String imagePrompt = "A general book cover about " + title; // 기본 이미지 프롬프트

        // 2. 응답 배열의 길이를 확인하고, 내용이 비어있지 않은지 확인하며 안전하게 값을 추출합니다.
        try {
            if (lines.length > 0 && lines[0] != null && !lines[0].trim().isEmpty()) {
                summary = lines[0].replace("1.", "").trim();
            }
            if (lines.length > 1 && lines[1] != null && !lines[1].trim().isEmpty()) {
                String priceStr = lines[1].replaceAll("[^0-9]", "");
                if (!priceStr.isEmpty()) { // 숫자만 추출한 결과가 비어있지 않을 때만 변환합니다.
                    price = Integer.parseInt(priceStr);
                }
            }
            if (lines.length > 2 && lines[2] != null && !lines[2].trim().isEmpty()) {
                imagePrompt = lines[2].replace("3.", "").trim();
            }
        } catch (Exception e) {
            System.err.println("GPT 응답 파싱 중 오류 발생. 기본값을 사용합니다. 오류: " + e.getMessage());
            // 파싱 중 어떤 오류가 나더라도, 위에서 설정한 기본값을 사용하게 되므로 시스템은 멈추지 않습니다.
        }

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