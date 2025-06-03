package lendev.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lendev.library.dto.BookDTO;
import lendev.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gerenciar requisições relacionadas a livros
@RestController
@RequestMapping("/api/books")  // Define o endpoint base para todas as rotas deste controlador
@Tag(name = "Books", description = "Endpoints para gerenciamento de livros") 
public class BookController {

    @Autowired
    private BookService bookService;  

    @PostMapping
    @Operation(summary = "Adicionar um novo livro")  
    public String addBook(@RequestBody BookDTO bookDTO) {
        
        return bookService.addBook(bookDTO);
    }

    @GetMapping
    @Operation(summary = "Buscar livros por título") 
    public List<BookDTO> searchBooks(@RequestParam(name = "title", required = false) String title) {
        
        return bookService.searchBooks(title);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados de um livro") 
    public String updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
       
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um livro")  
    public String deleteBook(@PathVariable Long id) {
        
        return bookService.deleteBook(id);
    }
}
