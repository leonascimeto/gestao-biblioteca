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

    // Injeta o repositório de livros para operações de persistência
    @Autowired
    private BookRepo bookRepo;

    
    public String addBook(BookDTO dto) {
        // Verifica se o livro já existe pelo ISBN
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

    // Lista todos os livros ou filtra por título (se informado)
    public List<BookDTO> getBooks(String title) {
        return bookRepo.findAll().stream()
            
            .filter(b -> title == null || b.title.toLowerCase().contains(title.toLowerCase()))
            
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    // Atualiza os dados de um livro existente pelo ID
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

    // Exclui um livro pelo ID
    public String deleteBook(Long id) {
        
        Book book = bookRepo.findById(id).orElse(null);

        
        if (book == null) return "Livro não encontrado";

        
        bookRepo.delete(book);

        return "Livro excluído com sucesso";
    }

    // Converte um objeto Book em um BookDTO (usado para resposta)
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
