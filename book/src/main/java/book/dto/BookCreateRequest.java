package book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 특정 책 생성을 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {
    private String title;
    private String content;
    private Long authorId;
}