package library.domain;

import java.util.*;
import library.domain.*;
import library.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Integer price;
}