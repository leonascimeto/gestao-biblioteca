package lendev.library.repo;

import lendev.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
