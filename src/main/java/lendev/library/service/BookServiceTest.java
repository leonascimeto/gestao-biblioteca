package lendev.library.service;
import lendev.library.dto.BookDTO;
import lendev.library.model.Book;
import lendev.library.repo.BookRepo;
import lendev.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;
    private FakeBookRepo fakeRepo;

    // FakeBookRepo simula o BookRepo real, armazenando livros em memória
    static class FakeBookRepo implements BookRepo {
        private Map<Long, Book> storage = new HashMap<>();
        private long idCounter = 1;

        @Override
        public Book findByIsbn(String isbn) {
            return storage.values().stream()
                    .filter(b -> isbn.equals(b.isbn))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Book> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public Optional<Book> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public <S extends Book> S save(S book) {
            if (book.id == null) {
                book.id = idCounter++;
            }
            storage.put(book.id, book);
            return book;
        }

        @Override
        public void delete(Book book) {
            storage.remove(book.id);
        }

		@Override
		public void flush() {
			
			
		}

		@Override
		public <S extends Book> S saveAndFlush(S entity) {
			
			return null;
		}

		@Override
		public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) {
			
			return null;
		}

		@Override
		public void deleteAllInBatch(Iterable<Book> entities) {
			
			
		}

		@Override
		public void deleteAllByIdInBatch(Iterable<Long> ids) {
			
			
		}

		@Override
		public void deleteAllInBatch() {
			
			
		}

		@Override
		public Book getOne(Long id) {
			
			return null;
		}

		@Override
		public Book getById(Long id) {
			
			return null;
		}

		@Override
		public Book getReferenceById(Long id) {
			
			return null;
		}

		@Override
		public <S extends Book> List<S> findAll(Example<S> example) {
			
			return null;
		}

		@Override
		public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
			
			return null;
		}

		@Override
		public <S extends Book> List<S> saveAll(Iterable<S> entities) {
			
			return null;
		}

		@Override
		public List<Book> findAllById(Iterable<Long> ids) {
			
			return null;
		}

		@Override
		public boolean existsById(Long id) {
			
			return false;
		}

		@Override
		public long count() {
			
			return 0;
		}

		@Override
		public void deleteById(Long id) {
			
			
		}

		@Override
		public void deleteAllById(Iterable<? extends Long> ids) {
			
			
		}

		@Override
		public void deleteAll(Iterable<? extends Book> entities) {
			
			
		}

		@Override
		public void deleteAll() {
			
			
		}

		@Override
		public List<Book> findAll(Sort sort) {
			
			return null;
		}

		@Override
		public Page<Book> findAll(Pageable pageable) {
			
			return null;
		}

		@Override
		public <S extends Book> Optional<S> findOne(Example<S> example) {
			
			return Optional.empty();
		}

		@Override
		public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
			
			return null;
		}

		@Override
		public <S extends Book> long count(Example<S> example) {
			
			return 0;
		}

		@Override
		public <S extends Book> boolean exists(Example<S> example) {
			
			return false;
		}

		@Override
		public <S extends Book, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
			
			return null;
		}

        
    }

    @BeforeEach
    public void setUp() {
        fakeRepo = new FakeBookRepo();
        bookService = new BookService();

        // Injetar manualmente o repositório fake
        try {
            var field = BookService.class.getDeclaredField("bookRepo");
            field.setAccessible(true);
            field.set(bookService, fakeRepo);
        } catch (Exception e) {
            fail("Erro ao configurar BookRepo fake");
        }
    }

    @Test
    public void testAddBook_Success() {
        BookDTO dto = new BookDTO();
        dto.setTitle("Livro 1");
        dto.setAuthor("Autor A");
        dto.setIsbn("123");
        dto.setGenre("Ficção");
        dto.setQuantity(5);

        String result = bookService.addBook(dto);

        assertEquals("Livro adicionado com sucesso", result);
        assertEquals(1, fakeRepo.findAll().size());
    }

    @Test
    public void testAddBook_Duplicado() {
        BookDTO dto = new BookDTO();
        dto.setTitle("Livro");
        dto.setIsbn("123");
        bookService.addBook(dto);

        BookDTO repetido = new BookDTO();
        repetido.setTitle("Outro Livro");
        repetido.setIsbn("123");

        String result = bookService.addBook(repetido);

        assertEquals("O livro já existe", result);
    }

    @Test
    public void testGetBooks_ComFiltro() {
        BookDTO dto1 = new BookDTO(); dto1.setTitle("Java"); dto1.setIsbn("1");
        BookDTO dto2 = new BookDTO(); dto2.setTitle("Python"); dto2.setIsbn("2");
        bookService.addBook(dto1);
        bookService.addBook(dto2);

        List<BookDTO> result = bookService.getBooks("java");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getTitle());
    }

    @Test
    public void testUpdateBook_Sucesso() {
        BookDTO dto = new BookDTO(); dto.setTitle("Original"); dto.setIsbn("123");
        bookService.addBook(dto);

        Book livro = fakeRepo.findByIsbn("123");
        BookDTO novo = new BookDTO();
        novo.setTitle("Atualizado");
        novo.setAuthor("Novo Autor");
        novo.setIsbn("456");
        novo.setGenre("Drama");
        novo.setQuantity(7);

        String result = bookService.updateBook(livro.id, novo);

        assertEquals("Livro atualizado com sucesso", result);
        assertEquals("Atualizado", fakeRepo.findById(livro.id).get().title);
    }

    @Test
    public void testUpdateBook_NaoEncontrado() {
        BookDTO dto = new BookDTO();
        String result = bookService.updateBook(999L, dto);
        assertEquals("Livro não encontrado", result);
    }

    @Test
    public void testDeleteBook_Sucesso() {
        BookDTO dto = new BookDTO(); dto.setTitle("Livro"); dto.setIsbn("abc");
        bookService.addBook(dto);

        Book livro = fakeRepo.findByIsbn("abc");
        String result = bookService.deleteBook(livro.id);

        assertEquals("Livro excluído com sucesso", result);
        assertEquals(0, fakeRepo.findAll().size());
    }

    @Test
    public void testDeleteBook_NaoEncontrado() {
        String result = bookService.deleteBook(123L);
        assertEquals("Livro não encontrado", result);
    }
}
