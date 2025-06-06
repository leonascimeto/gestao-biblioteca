package lendev.library.service;

import lendev.library.model.Book;
import lendev.library.model.Loan;
import lendev.library.model.Student;
import lendev.library.repo.BookRepo;
import lendev.library.repo.LoanRepo;
import lendev.library.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepo loanRepo;

    @Mock
    private BookRepo bookRepo;

    @Mock
    private StudentRepo studentRepo;

    private Student criarAluno(Long id, int penaltyCount) {
        Student s = new Student();
        s.id = id;
        s.penaltyCount = penaltyCount;
        return s;
    }

    private Book criarLivro(Long id, int quantity) {
        Book b = new Book();
        b.id = id;
        b.quantity = quantity;
        return b;
    }

    private Loan criarEmprestimo(Long id, Long studentId, Long bookId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        Loan l = new Loan();
        l.id = id;
        l.studentId = studentId;
        l.bookId = bookId;
        l.loanDate = loanDate;
        l.dueDate = dueDate;
        l.returnDate = returnDate;
        return l;
    }

    @Test
    void deveEmprestarLivroComSucesso() {
        Student aluno = criarAluno(1L, 0);
        Book livro = criarLivro(1L, 2);

        when(studentRepo.findById(1L)).thenReturn(Optional.of(aluno));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(livro));
        when(loanRepo.findByStudentIdAndReturnDateIsNull(1L)).thenReturn(List.of());

        String resultado = loanService.loanBook(1L, 1L);

        verify(bookRepo).save(any());
        verify(loanRepo).save(any());
        assertEquals("Livro emprestado", resultado);
    }

    @Test
    void deveRecusarEmprestimoSeAlunoInexistente() {
        when(studentRepo.findById(1L)).thenReturn(Optional.empty());

        String resultado = loanService.loanBook(1L, 1L);
        assertEquals("Aluno ou livro não encontrado", resultado);
    }

    @Test
    void deveRecusarEmprestimoSeLivroInexistente() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(criarAluno(1L, 0)));
        when(bookRepo.findById(1L)).thenReturn(Optional.empty());

        String resultado = loanService.loanBook(1L, 1L);
        assertEquals("Aluno ou livro não encontrado", resultado);
    }

    @Test
    void deveRecusarEmprestimoSeAlunoBanido() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(criarAluno(1L, 5)));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(criarLivro(1L, 1)));

        String resultado = loanService.loanBook(1L, 1L);
        assertEquals("Aluno banido permanentemente", resultado);
    }

    @Test
    void deveRecusarEmprestimoSeLivroIndisponivel() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(criarAluno(1L, 0)));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(criarLivro(1L, 0)));

        String resultado = loanService.loanBook(1L, 1L);
        assertEquals("Livro indisponível", resultado);
    }

    @Test
    void deveRecusarEmprestimoSeAlunoJaTemLivro() {
        when(studentRepo.findById(1L)).thenReturn(Optional.of(criarAluno(1L, 0)));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(criarLivro(1L, 1)));
        when(loanRepo.findByStudentIdAndReturnDateIsNull(1L)).thenReturn(List.of(new Loan()));

        String resultado = loanService.loanBook(1L, 1L);
        assertEquals("O aluno já tem um livro", resultado);
    }

    @Test
    void deveDevolverLivroComSucessoDentroDoPrazo() {
        Loan emprestimo = criarEmprestimo(1L, 1L, 1L, LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), null);
        Book livro = criarLivro(1L, 0);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(livro));

        String resultado = loanService.returnBook(1L);

        verify(loanRepo).save(any());
        verify(bookRepo).save(any());
        assertEquals("Livro devolvido", resultado);
    }

    @Test
    void deveAplicarPenalidadeSeDevolverAtrasado() {
        Loan emprestimo = criarEmprestimo(1L, 1L, 1L, LocalDate.now().minusDays(10), LocalDate.now().minusDays(3), null);
        Book livro = criarLivro(1L, 1);
        Student aluno = criarAluno(1L, 1);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(bookRepo.findById(1L)).thenReturn(Optional.of(livro));
        when(studentRepo.findById(1L)).thenReturn(Optional.of(aluno));

        String resultado = loanService.returnBook(1L);

        verify(studentRepo).save(any());
        assertEquals("Livro devolvido", resultado);
        assertEquals(2, aluno.penaltyCount);
    }

    @Test
    void deveRecusarDevolucaoSeJaDevolvido() {
        Loan emprestimo = criarEmprestimo(1L, 1L, 1L, LocalDate.now().minusDays(10), LocalDate.now().minusDays(3), LocalDate.now());

        when(loanRepo.findById(1L)).thenReturn(Optional.of(emprestimo));

        String resultado = loanService.returnBook(1L);

        assertEquals("Já retornou ou não foi encontrado", resultado);
    }

    @Test
    void deveListarEmprestimosPendentes() {
        when(loanRepo.findByReturnDateIsNull()).thenReturn(List.of(criarEmprestimo(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), null)));

        List<?> resultado = loanService.listLoans("pendente");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveListarEmprestimosDevolvidos() {
        when(loanRepo.findByReturnDateIsNotNull()).thenReturn(List.of(criarEmprestimo(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), LocalDate.now())));

        List<?> resultado = loanService.listLoans("retornou");

        assertEquals(1, resultado.size());
    }

    @Test
    void deveListarTodosEmprestimos() {
        when(loanRepo.findAll()).thenReturn(List.of(criarEmprestimo(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), null)));

        List<?> resultado = loanService.listLoans(null);

        assertEquals(1, resultado.size());
    }
}
