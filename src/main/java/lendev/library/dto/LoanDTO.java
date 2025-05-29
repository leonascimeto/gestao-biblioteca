package lendev.library.dto;

import java.time.LocalDate;

// Classe DTO (Data Transfer Object) para transferência de dados de empréstimos
public class LoanDTO {
	 public Long id;
	    public Long studentId;
	    public Long bookId;
	    public LocalDate loanDate;
	    public LocalDate dueDate;
	    public LocalDate returnDate;

}