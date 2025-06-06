package lendev.library.dto;

import java.time.LocalDate;


public class LoanDTO {
	 public Long id;
	    public Long studentId;
	    public Long bookId;
	    public LocalDate loanDate;
	    public LocalDate dueDate;
	    public LocalDate returnDate;

}