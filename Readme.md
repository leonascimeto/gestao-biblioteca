## Sistema de Gerenciamento de Biblioteca Escolar

Desenvolvimento de um sistema de gerenciamento de biblioteca escolar para registro, busca e controle de livros e alunos, promovendo eficiência e organização

### Casos de Uso:
- [ ] Registrar Novo Livro
- [ ] Atualizar Informações do Livro
- [ ] Excluir Livro do Sistema
- [ ] Buscar Livro por Título, Autor ou ISBN
- [ ] Verificar Disponibilidade de Livro
- [ ] Registrar Novo Aluno
- [ ] Atualizar Informações do Aluno
- [ ] Excluir Aluno do Sistema
- [ ] Registrar Empréstimo de Livro
- [ ] Registrar Devolução de Livro
- [ ] Buscar emprestimos

### Requisitos Funcionais:
- [ ] Registrar Novo Livro
    - [ ] O Usuario deve fornecer informações como título, autor, ISBN, gênero e quantidade
    - [ ] O ISBN deve ser único
- [ ] Buscar Livros por Título, Autor ou ISBN
    - [ ] O Usuário pode buscar livros por título, autor ou ISBN
- [ ] Registrar Novo Aluno
    - [ ] O Usuário devem fornecer informações como nome, matricula
    - [ ] A Matricula deve ser única
- [ ] Registrar Empréstimo de Livro
    - [ ] O usuario deve fornecer o ID do aluno e o ID do livro emprestado
    - [ ] Não deve emprestar livros que não estão disponíveis
    - [ ] Não deve emprestar livros para alunos que já tem livro emprestado
    - [ ] Não deve emprestar livros para alunos que estiveram com 5 penalidade
    - [ ] Deve registrar a data de empréstimo
    - [ ] Deve registrar a data que o livro deve ser devolvido, após 7 dias
- [ ] Registrar Devolução de Livro
    - [ ] Deve registrar a data de devolução do livro
    - [ ] Deve adicionar aluno registrar penalidades caso aluno tenha devolvido após data limite
- [ ] Buscar emprestimos
    - [ ] Os bibliotecários podem visualizar detalhes de cada emprestimo, como data de emprestimo e data de devolução, aluno e livro emprestado
    - [ ] Deve ser possivel filtrar por pendentes e devolvidos



Documentação:📚 Lendev Library API

.API desenvolvida para gerenciar livros, alunos e empréstimos em uma biblioteca. O sistema permite cadastrar, consultar, atualizar e remover dados de livros, alunos e empréstimos de maneira simples e organizada.

.⚖ Tecnologias Utilizadas

.Java 17

.Spring Boot

.Spring Data JPA

Swagger/OpenAPI

.H2 Database (ou outro configurado)

📁 Estrutura do Projeto

📂 lendev.library

LibraryApplication.java

.Classe principal que inicializa a aplicação com Spring Boot.

LibraryApplicationTests.java

.Teste básico para verificar o carregamento do contexto da aplicação.

DataLoader.java

.Classe para popular o banco de dados com dados iniciais (livros e alunos) ao iniciar a aplicação.

CorsConfig.java

.Libera o CORS para todas as origens, métodos e cabeçalhos.

📂 lendev.library.config

SwaggerConfig.java

.Configuração do Swagger/OpenAPI para documentação da API.

📂 lendev.library.controller

BookController.java

.Endpoints REST para gerenciamento de livros.

.POST /api/books

.GET /api/books

.PUT /api/books/{id}

.DELETE /api/books/{id}

LoanController.java

.Endpoints REST para gerenciamento de empréstimos.

.POST /api/loans

.POST /api/loans/returns/{id}

.GET /api/loans

StudentController.java

.Endpoints REST para gerenciamento de alunos.

.POST /api/students

.GET /api/students

.PUT /api/students/{id}

.DELETE /api/students/{id}

📂 lendev.library.dto

BookDTO.java

.DTO para transferência de dados de livros.

LoanDTO.java

.DTO para transferência de dados de empréstimos.

StudentDTO.java

.DTO para transferência de dados de alunos.

📂 lendev.library.repo

BookRepo.java

.Repositório JPA para a entidade Book. Contém método findByIsbn(String).

LoanRepo.java

.Repositório JPA para a entidade Loan. Contém métodos:

.findByReturnDateIsNull()

.findByStudentIdAndReturnDateIsNull(Long)

.findByReturnDateIsNotNull()

StudentRepo.java

.Repositório JPA para a entidade Student. Contém método findByRegistration(String).

📂 lendev.library.service

BookService.java

.Lógica de negócio para livros. Inclui operações de CRUD e busca por título.

StudentService.java

.Lógica de negócio simulada para alunos utilizando lista em memória (sem persistência real).

📃 Observações

.O projeto usa Swagger para documentação interativa da API. Após rodar o sistema, acesse: http://localhost:8080/swagger-ui.html

.As classes DTO evitam a exposição direta das entidades do banco de dados.

📊 Futuras Melhorias

.Substituir StudentService por uma implementação baseada em repositório.

.Implementar autenticação e controle de acesso.

.Adicionar tratamento de exceções.

.Melhorar as validações dos dados.

✅ Autor

/Desenvolvido por Leonardo,Thiago, Junior./
