package lendev.library.controller;

import lendev.library.model.*;
import lendev.library.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class LibraryController {

    @Autowired
    public BookRepo bookRepo;

    @Autowired
    public StudentRepo studentRepo;

    @Autowired
    public LoanRepo loanRepo;

    // ─────────────── BOOKS ───────────────

    @PostMapping("/books")
    public String addBook(@RequestBody Map<String, Object> body) {
        Book b = new Book();
        b.title = (String) body.get("title");
        b.author = (String) body.get("author");
        b.isbn = (String) body.get("isbn");
        b.genre = (String) body.get("genre");
        b.quantity = (Integer) body.get("quantity");

        if (bookRepo.findByIsbn(b.isbn) != null) {
            return "Book already exists";
        }

        bookRepo.save(b);
        return "Book added successfully";
    }

    @GetMapping("/books")
    public List<Book> searchBooks(@RequestParam(name = "title", required = false) String title) {
        List<Book> all = bookRepo.findAll();
        List<Book> result = new ArrayList<>();

        for (Book b : all) {
            if (title == null || b.title.toLowerCase().contains(title.toLowerCase())) {
                result.add(b);
            }
        }

        return result;
    }

    @PutMapping("/books/{id}")
    public String updateBook(@PathVariable("id") Long id, @RequestBody Map<String, Object> body) {
        Book book = bookRepo.findById(id).orElse(null);

        if (book == null) {
            return "Book not found";
        }

        book.title = (String) body.get("title");
        book.author = (String) body.get("author");
        book.isbn = (String) body.get("isbn");
        book.genre = (String) body.get("genre");
        book.quantity = (Integer) body.get("quantity");

        bookRepo.save(book);
        return "Book updated successfully";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        Book book = bookRepo.findById(id).orElse(null);
        if (book == null) {
            return "Book not found";
        }

        bookRepo.delete(book);
        return "Book deleted successfully";
    }

    // ─────────────── STUDENTS ───────────────

    @PostMapping("/students")
    public String addStudent(@RequestBody Map<String, Object> body) {
        Student s = new Student();
        s.name = (String) body.get("name");
        s.registration = (String) body.get("registration");

        if (studentRepo.findByRegistration(s.registration) != null) {
            return "Registration already exists";
        }

        studentRepo.save(s);
        return "Student added";
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @PutMapping("/students/{id}")
    public String updateStudent(@PathVariable("id") Long id, @RequestBody Map<String, Object> body) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return "Student not found";
        }

        student.name = (String) body.get("name");
        student.registration = (String) body.get("registration");

        studentRepo.save(student);
        return "Student updated successfully";
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        Student student = studentRepo.findById(id).orElse(null);
        if (student == null) {
            return "Student not found";
        }

        studentRepo.delete(student);
        return "Student deleted successfully";
    }

    // ─────────────── LOANS ───────────────

    @PostMapping("/loans")
    public String loanBook(@RequestBody Map<String, Object> req) {
        Long studentId = Long.valueOf((Integer) req.get("studentId"));
        Long bookId = Long.valueOf((Integer) req.get("bookId"));

        Student s = studentRepo.findById(studentId).orElse(null);
        Book b = bookRepo.findById(bookId).orElse(null);

        if (s != null && b != null) {
            if (s.penaltyCount < 5) {
                if (b.quantity > 0) {
                    List<Loan> x = loanRepo.findByStudentIdAndReturnDateIsNull(studentId);
                    if (x.isEmpty()) {
                        Loan l = new Loan();
                        l.studentId = studentId;
                        l.bookId = bookId;
                        l.loanDate = LocalDate.now();
                        l.dueDate = LocalDate.now().plusDays(7);

                        b.quantity--;
                        bookRepo.save(b);
                        loanRepo.save(l);

                        return "Book loaned";
                    } else {
                        return "Student already has a book";
                    }
                } else {
                    return "Book unavailable";
                }
            } else {
                return "Student permanently banned";
            }
        } else {
            return "Student or book not found";
        }
    }

    @PostMapping("/loans/returns/{id}")
    public String returnBook(@PathVariable("id") Long loanId) {
        Loan l = loanRepo.findById(loanId).orElse(null);

        if (l != null) {
            if (l.returnDate == null) {
                l.returnDate = LocalDate.now();
                loanRepo.save(l);

                Book x = bookRepo.findById(l.bookId).orElse(null);
                if (x != null) {
                    x.quantity++;
                    bookRepo.save(x);
                }

                if (l.returnDate.isAfter(l.dueDate)) {
                    Student y = studentRepo.findById(l.studentId).orElse(null);
                    if (y != null) {
                        y.penaltyCount++;
                        studentRepo.save(y);
                    }
                }

                return "Book returned";
            } else {
                return "Already returned or not found";
            }
        } else {
            return "Already returned or not found";
        }
    }

    @GetMapping("/loans")
    public List<Loan> listLoans(@RequestParam(name = "status", required = false) String status) {
        List<Loan> loans = new ArrayList<>();

        if ("pending".equalsIgnoreCase(status)) {
            loans = loanRepo.findByReturnDateIsNull();
        } else if ("returned".equalsIgnoreCase(status)) {
            loans = loanRepo.findByReturnDateIsNotNull();
        } else {
            loans = loanRepo.findAll();
        }

        return loans;
    }
}
