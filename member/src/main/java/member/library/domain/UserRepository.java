package member.library.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// @RepositoryRestResource(collectionResourceRel = "users", path = "users")
@RepositoryRestResource
public interface UserRepository
        extends PagingAndSortingRepository<User, Long> {

    // --- 이 부분을 새로 추가해주세요 ---
    User save(User entity);
}