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