package book.infra;

import book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaBookRepository extends JpaRepository<Book, Long> {
}