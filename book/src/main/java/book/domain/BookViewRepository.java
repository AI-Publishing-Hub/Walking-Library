package book.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookViewRepository extends JpaRepository<BookView, Long> {
    List<BookView> findAllByIsBookPublishedTrue();
    List<BookView> findAllByIsBestsellerTrue();
}