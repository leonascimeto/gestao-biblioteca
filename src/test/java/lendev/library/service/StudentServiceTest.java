package lendev.library.service;

import lendev.library.dto.StudentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentServiceTest {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }

    private StudentDTO criarAlunoMock(String nome) {
        StudentDTO aluno = new StudentDTO(null, null, null); // usando o construtor vazio
        aluno.name = nome;
        aluno.registration = "RA" + nome.hashCode();
        aluno.penaltyCount = 0;
        return aluno;
    }

    @Test
    void deveAdicionarAlunoComSucesso() {
        StudentDTO aluno = criarAlunoMock("Leonardo");

        String resultado = studentService.addStudent(aluno);

        List<StudentDTO> lista = studentService.getAllStudents();

        assertEquals("Aluno cadastrado com sucesso!", resultado);
        assertEquals(1, lista.size());
        assertEquals("Leonardo", lista.get(0).name);
    }

    @Test
    void deveListarTodosOsAlunos() {
        studentService.addStudent(criarAlunoMock("Leonardo"));
        studentService.addStudent(criarAlunoMock("Juliana"));

        List<StudentDTO> alunos = studentService.getAllStudents();

        assertEquals(2, alunos.size());
    }

    @Test
    void deveAtualizarAlunoComSucesso() {
        studentService.addStudent(criarAlunoMock("Leonardo"));

        StudentDTO novoAluno = criarAlunoMock("Marcos");
        String resultado = studentService.updateStudent(0L, novoAluno);

        List<StudentDTO> alunos = studentService.getAllStudents();

        assertEquals("Aluno atualizado com sucesso!", resultado);
        assertEquals("Marcos", alunos.get(0).name);
    }

    @Test
    void deveFalharAoAtualizarAlunoInexistente() {
        StudentDTO aluno = criarAlunoMock("Carlos");

        String resultado = studentService.updateStudent(5L, aluno);

        assertEquals("Aluno não encontrado.", resultado);
    }

    @Test
    void deveRemoverAlunoComSucesso() {
        studentService.addStudent(criarAlunoMock("Leonardo"));

        String resultado = studentService.deleteStudent(0L);

        assertEquals("Aluno removido com sucesso!", resultado);
        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    void deveFalharAoRemoverAlunoInexistente() {
        String resultado = studentService.deleteStudent(2L);

        assertEquals("Aluno não encontrado.", resultado);
    }
}