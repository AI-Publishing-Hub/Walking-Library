package book.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    /**
     * 저자 ID로 책 목록을 조회합니다.
     * @param authorId 저자 ID
     * @return 책 목록
     */
    List<Book> findByAuthorId(Long authorId); //

    Optional<Book> findById(Long id); //

    Book save(Book book); //

    /**
     * 출판된(isBookPublished=true) 모든 책 목록을 조회합니다.
     * @return 출판된 책 목록
     */
    List<Book> findAllByIsBookPublishedTrue();

    /**
     * 베스트셀러(isBestseller=true)인 모든 책 목록을 조회합니다.
     * @return 베스트셀러 책 목록
     */
    List<Book> findAllByIsBestsellerTrue();
}