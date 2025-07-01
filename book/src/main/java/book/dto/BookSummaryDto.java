package book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 책 목록(전체, 베스트셀러) 조회를 위한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSummaryDto {
    private Long id;
    private String title;
    private Long authorId;
    private Boolean isBestseller;
    private String summary;
    private Long viewCount;
    private String bookCoverUrl;
    private Integer price;
}