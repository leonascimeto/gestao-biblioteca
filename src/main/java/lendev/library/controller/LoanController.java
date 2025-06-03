package lendev.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lendev.library.dto.LoanDTO;
import lendev.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gerenciar requisições relacionadas a empréstimos de livros
@RestController
@RequestMapping("/api/loans")  
@Tag(name = "Loans", description = "Endpoints para gerenciamento de empréstimos") // Documentação Swagger/OpenAPI para agrupar os endpoints
public class LoanController {

    @Autowired
    private LoanService loanService;  

    @PostMapping
    @Operation(summary = "Realizar empréstimo de livro")  
    public String loanBook(@RequestBody LoanDTO loanDTO) {
        
        return loanService.loanBook(loanDTO);
    }

    @PostMapping("/returns/{id}")
    @Operation(summary = "Realizar devolução de livro")  
    public String returnBook(@PathVariable Long id) {
        
        return loanService.returnBook(id);
    }

    @GetMapping
    @Operation(summary = "Listar empréstimos por status")  
    public List<LoanDTO> listLoans(@RequestParam(name = "status", required = false) String status) {
        // Recebe parâmetro opcional 'status' para filtrar empréstimos (ex: pendente, retornou) e retorna a lista filtrada
        return loanService.listLoans(status);
    }
}
