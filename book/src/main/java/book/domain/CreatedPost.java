package book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatedPost {

    private Long id;
    private String title;
    private String content;
}
