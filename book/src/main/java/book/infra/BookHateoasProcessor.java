package book.infra;

import book.domain.Book;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

// 일종의 msa의 구조(프론트에서 백엔드 상의 순서를 모른 다는 전재로 사용됨)
@Component
public class BookHateoasProcessor implements RepresentationModelProcessor<EntityModel<Book>> {

    @Override
    public EntityModel<Book> process(EntityModel<Book> model) {
        return model;
    }
}