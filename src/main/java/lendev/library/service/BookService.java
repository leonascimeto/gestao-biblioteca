package lendev.library.service;

import lendev.library.dto.BookDTO;
import lendev.library.model.Book;
import lendev.library.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    
    @Autowired
    private BookRepo bookRepo;

    
    public String addBook(BookDTO dto) {
        
        if (bookRepo.findByIsbn(dto.getIsbn()) != null) return "O livro já existe";

        
        Book book = new Book();
        book.title = dto.getTitle();
        book.author = dto.getAuthor();
        book.isbn = dto.getIsbn();
        book.genre = dto.getGenre();
        book.quantity = dto.getQuantity();

        
        bookRepo.save(book);

        return "Livro adicionado com sucesso";
    }

    
    public List<BookDTO> getBooks(String title) {
        return bookRepo.findAll().stream()
            
            .filter(b -> title == null || b.title.toLowerCase().contains(title.toLowerCase()))
            
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    
    public String updateBook(Long id, BookDTO dto) {
        
        Book book = bookRepo.findById(id).orElse(null);

        
        if (book == null) return "Livro não encontrado";

        
        book.title = dto.getTitle();
        book.author = dto.getAuthor();
        book.isbn = dto.getIsbn();
        book.genre = dto.getGenre();
        book.quantity = dto.getQuantity();

        
        bookRepo.save(book);

        return "Livro atualizado com sucesso";
    }

    
    public String deleteBook(Long id) {
        
        Book book = bookRepo.findById(id).orElse(null);

        
        if (book == null) return "Livro não encontrado";

        
        bookRepo.delete(book);

        return "Livro excluído com sucesso";
    }

    
    private BookDTO toDTO(Book b) {
        BookDTO dto = new BookDTO();
        dto.setId(b.id);
        dto.setTitle(b.title);
        dto.setAuthor(b.author);
        dto.setIsbn(b.isbn);
        dto.setGenre(b.genre);
        dto.setQuantity(b.quantity);
        return dto;
    }

    // Método ainda não implementado, reservado para busca avançada por título
    public List<BookDTO> searchBooks(String title) {
        
        return null;
    }
}
