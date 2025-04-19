package lendev.library.repo;

import lendev.library.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {
    Student findByRegistration(String registration);
}