package lendev.library.service;

import lendev.library.dto.StudentDTO;
import lendev.library.repo.StudentRepo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    // Simulação de repositório em memória usando uma lista (para fins de exemplo).
    
    private List<StudentDTO> students = new ArrayList<>();
    
	public StudentRepo studentRepo;

    
    public String addStudent(StudentDTO studentDTO) {
        students.add(studentDTO);
        return "Aluno cadastrado com sucesso!";
    }

    
    public List<StudentDTO> getAllStudents() {
        return students;
    }

    
    public String updateStudent(Long id, StudentDTO studentDTO) {
        
        if (id < students.size()) {
            students.set(id.intValue(), studentDTO);
            return "Aluno atualizado com sucesso!";
        }
        return "Aluno não encontrado.";
    }

    
    public String deleteStudent(Long id) {
        
        if (id < students.size()) {
            students.remove(id.intValue());
            return "Aluno removido com sucesso!";
        }
        return "Aluno não encontrado.";
    }
}
