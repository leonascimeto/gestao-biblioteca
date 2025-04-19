package lendev.library.repo;

import lendev.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepo extends JpaRepository<Loan, Long> {
    List<Loan> findByReturnDateIsNull();
    List<Loan> findByStudentIdAndReturnDateIsNull(Long studentId);
    List<Loan> findByReturnDateIsNotNull();
}
