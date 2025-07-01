package book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 특정 책 상세 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDto {
    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private Long authorId;
    private Boolean isBestseller;
    private String bookCoverUrl;
    private Integer price;
}