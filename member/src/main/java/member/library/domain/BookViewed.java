package member.library.domain;

import java.util.*;

import lombok.*;
import member.library.domain.*;
import member.library.infra.AbstractEvent;

@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long id;
    private Long memberId;
    private Integer price;
}