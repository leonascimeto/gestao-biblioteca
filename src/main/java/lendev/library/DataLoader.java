package lendev.library;

import lendev.library.model.*;
import lendev.library.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(BookRepo bookRepo, StudentRepo studentRepo) {
        return args -> {
            if (bookRepo.findAll().isEmpty()) {
                Book b1 = new Book();
                b1.title = "Clean Code";
                b1.author = "Robert C. Martin";
                b1.isbn = "1234567890";
                b1.genre = "Programming";
                b1.quantity = 3;

                Book b2 = new Book();
                b2.title = "Refactoring";
                b2.author = "Martin Fowler";
                b2.isbn = "0987654321";
                b2.genre = "Software Engineering";
                b2.quantity = 2;

                bookRepo.saveAll(List.of(b1, b2));
            }

            if (studentRepo.findAll().isEmpty()) {
                Student s1 = new Student();
                s1.name = "Alice";
                s1.registration = "A001";

                Student s2 = new Student();
                s2.name = "Bob";
                s2.registration = "B002";

                studentRepo.saveAll(List.of(s1, s2));
            }
        };
    }
}
