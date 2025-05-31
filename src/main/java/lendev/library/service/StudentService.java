package lendev.library.service;

import lendev.library.dto.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    // Simulação de repositório em memória usando uma lista (para fins de exemplo).
    
    private List<StudentDTO> students = new ArrayList<>();

    
    public String addStudent(StudentDTO studentDTO) {
        students.add(studentDTO);
        return "Aluno cadastrado com sucesso!";
    }

    
    public List<StudentDTO> getAllStudents() {
        return students;
    }

    // Atualiza um aluno existente com base no índice fornecido como ID
    public String updateStudent(Long id, StudentDTO studentDTO) {
        // Verifica se o ID fornecido é válido dentro dos limites da lista
        // OBS: Isso é apenas uma simulação — normalmente, se usaria um ID real e buscaria o aluno no banco
        if (id < students.size()) {
            students.set(id.intValue(), studentDTO);
            return "Aluno atualizado com sucesso!";
        }
        return "Aluno não encontrado.";
    }

    // Remove um aluno da lista com base no índice (ID)
    public String deleteStudent(Long id) {
        
        if (id < students.size()) {
            students.remove(id.intValue());
            return "Aluno removido com sucesso!";
        }
        return "Aluno não encontrado.";
    }
}
