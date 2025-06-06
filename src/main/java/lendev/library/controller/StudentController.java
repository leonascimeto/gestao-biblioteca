package lendev.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lendev.library.dto.StudentDTO;
import lendev.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST para gerenciar requisições relacionadas a alunos
@RestController
@RequestMapping("/api/students")  
@Tag(name = "Students", description = "Endpoints para gerenciamento de alunos") 
public class StudentController {

    @Autowired
    private StudentService studentService;  

    @PostMapping
    @Operation(summary = "Cadastrar novo aluno")  
    public String addStudent(@RequestBody StudentDTO studentDTO) {
        
        return studentService.addStudent(studentDTO);
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos")  
    public List<StudentDTO> getAllStudents() {
        
        return studentService.getAllStudents();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do aluno")  
    public String updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover aluno")  
    public String deleteStudent(@PathVariable Long id) {
        
        return studentService.deleteStudent(id);
    }
}
