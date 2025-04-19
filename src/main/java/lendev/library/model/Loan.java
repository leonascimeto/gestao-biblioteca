package lendev.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue
    public Long id;

    public Long studentId;
    public Long bookId;
    public LocalDate loanDate;
    public LocalDate dueDate;
    public LocalDate returnDate;
}
