package lendev.library.service;

import lendev.library.dto.LoanDTO;
import lendev.library.model.*;
import lendev.library.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    
    @Autowired
    private LoanRepo loanRepo;

    
    @Autowired
    private BookRepo bookRepo;

   
    @Autowired
    private StudentRepo studentRepo;

    // Realiza o empréstimo de um livro para um aluno
    public String loanBook(Long studentId, Long bookId) {
        
        Student student = studentRepo.findById(studentId).orElse(null);
        Book book = bookRepo.findById(bookId).orElse(null);

        // Validações de regras de negócio antes de permitir o empréstimo
        if (student == null || book == null) return "Aluno ou livro não encontrado";
        if (student.penaltyCount >= 5) return "Aluno banido permanentemente";
        if (book.quantity <= 0) return "Livro indisponível";
        if (!loanRepo.findByStudentIdAndReturnDateIsNull(studentId).isEmpty()) return "O aluno já tem um livro";

        
        Loan loan = new Loan();
        loan.studentId = studentId;
        loan.bookId = bookId;
        loan.loanDate = LocalDate.now();
        loan.dueDate = LocalDate.now().plusDays(7); // Define data de devolução para 7 dias

        
        book.quantity--;
        bookRepo.save(book);
        loanRepo.save(loan);

        return "Livro emprestado";
    }

    // Realiza a devolução de um livro emprestado
    public String returnBook(Long loanId) {
        
        Loan loan = loanRepo.findById(loanId).orElse(null);

        
        if (loan == null || loan.returnDate != null) return "Já retornou ou não foi encontrado";

        
        loan.returnDate = LocalDate.now();
        loanRepo.save(loan);

        // Atualiza a quantidade de livros disponíveis ao devolver
        Book book = bookRepo.findById(loan.bookId).orElse(null);
        if (book != null) {
            book.quantity++;
            bookRepo.save(book);
        }

        // Aplica penalidade se a devolução for após a data limite
        if (loan.returnDate.isAfter(loan.dueDate)) {
            Student student = studentRepo.findById(loan.studentId).orElse(null);
            if (student != null) {
                student.penaltyCount++;
                studentRepo.save(student);
            }
        }

        return "Livro devolvido";
    }

    // Lista os empréstimos de acordo com o status (pendente, retornou ou todos)
    public List<LoanDTO> listLoans(String status) {
        
        List<Loan> loans = switch (status != null ? status.toLowerCase() : "") {
            case "pendente" -> loanRepo.findByReturnDateIsNull(); 
            case "retornou" -> loanRepo.findByReturnDateIsNotNull(); 
            default -> loanRepo.findAll(); 
        };

        
        return loans.stream().map(this::toDTO).collect(Collectors.toList());
    }

    
    private LoanDTO toDTO(Loan l) {
        LoanDTO dto = new LoanDTO();
        dto.id = l.id;
        dto.studentId = l.studentId;
        dto.bookId = l.bookId;
        dto.loanDate = l.loanDate;
        dto.dueDate = l.dueDate;
        dto.returnDate = l.returnDate;
        return dto;
    }

    // Método sobrescrito, mas ainda não implementado (para futura funcionalidade com DTO diretamente)
    public String loanBook(LoanDTO loanDTO) {
        
        return null;
    }
}
