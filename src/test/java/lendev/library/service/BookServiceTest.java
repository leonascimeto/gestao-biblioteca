package lendev.library.service;

import lendev.library.dto.BookDTO;
import lendev.library.model.Book;
import lendev.library.repo.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepo bookRepo;

    private Book criarBookMock(Long id) {
        Book b = new Book();
        b.id = id;
        b.title = "Clean Code";
        b.author = "Robert C. Martin";
        b.isbn = "9780132350884";
        b.genre = "Programming";
        b.quantity = 10;
        return b;
    }

    private BookDTO criarBookDTO() {
        BookDTO dto = new BookDTO();
        dto.setTitle("Clean Code");
        dto.setAuthor("Robert C. Martin");
        dto.setIsbn("9780132350884");
        dto.setGenre("Programming");
        dto.setQuantity(10);
        return dto;
    }

    @Test
    void deveAdicionarLivroComSucesso() {
        BookDTO dto = criarBookDTO();

        when(bookRepo.findByIsbn(dto.getIsbn())).thenReturn(null);

        String result = bookService.addBook(dto);

        verify(bookRepo, times(1)).save(any(Book.class));
        assertEquals("Livro adicionado com sucesso", result);
    }

    @Test
    void naoDeveAdicionarLivroSeIsbnJaExistir() {
        BookDTO dto = criarBookDTO();
        when(bookRepo.findByIsbn(dto.getIsbn())).thenReturn(criarBookMock(1L));

        String result = bookService.addBook(dto);

        verify(bookRepo, never()).save(any(Book.class));
        assertEquals("O livro já existe", result);
    }

    @Test
    void deveListarTodosOsLivros() {
        List<Book> books = Arrays.asList(criarBookMock(1L), criarBookMock(2L));
        when(bookRepo.findAll()).thenReturn(books);

        List<BookDTO> result = bookService.getBooks(null);

        assertEquals(2, result.size());
    }

    @Test
    void deveFiltrarLivrosPeloTitulo() {
        Book b1 = criarBookMock(1L);
        b1.title = "Clean Code";
        Book b2 = criarBookMock(2L);
        b2.title = "The Pragmatic Programmer";

        when(bookRepo.findAll()).thenReturn(List.of(b1, b2));

        List<BookDTO> result = bookService.getBooks("pragmatic");

        assertEquals(1, result.size());
        assertEquals("The Pragmatic Programmer", result.get(0).getTitle());
    }

    @Test
    void deveAtualizarLivroComSucesso() {
        BookDTO dto = criarBookDTO();
        Book existing = criarBookMock(1L);

        when(bookRepo.findById(1L)).thenReturn(Optional.of(existing));

        String result = bookService.updateBook(1L, dto);

        verify(bookRepo).save(existing);
        assertEquals("Livro atualizado com sucesso", result);
    }

    @Test
    void naoDeveAtualizarLivroInexistente() {
        BookDTO dto = criarBookDTO();
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        String result = bookService.updateBook(1L, dto);

        verify(bookRepo, never()).save(any());
        assertEquals("Livro não encontrado", result);
    }

    @Test
    void deveExcluirLivroComSucesso() {
        Book existing = criarBookMock(1L);
        when(bookRepo.findById(1L)).thenReturn(Optional.of(existing));

        String result = bookService.deleteBook(1L);

        verify(bookRepo).delete(existing);
        assertEquals("Livro excluído com sucesso", result);
    }

    @Test
    void naoDeveExcluirLivroInexistente() {
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        String result = bookService.deleteBook(1L);

        verify(bookRepo, never()).delete(any());
        assertEquals("Livro não encontrado", result);
    }
}
