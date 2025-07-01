package member.library.infra;

import member.library.domain.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserHateoasProcessor implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        User user = model.getContent();
        if (user != null && user.getId() != null) {
            // self 링크는 이미 자동으로 추가되지만, 예제로 구독 변경 링크 추가
            model.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
            model.add(linkTo(methodOn(UserController.class).updateUser(user.getId(), user))
                    .withRel("update-user"));
            model.add(linkTo(methodOn(UserController.class).deleteUser(user.getId()))
                    .withRel("delete-user"));
        }
        return model;
    }
}