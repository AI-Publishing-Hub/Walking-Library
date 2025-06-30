package book.domain;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    /**
     * 저자 ID로 책 목록을 조회합니다.
     * @param authorId 저자 ID
     * @return 책 목록
     */
    List<Book> findByAuthorId(Long authorId);
}