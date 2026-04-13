# Sistema de Controle de Cursos

Este projeto é um sistema desenvolvido para registrar dados de uma escola de cursos livres.  O objetivo central é gerenciar o cadastro de alunos e cursos, além de registrar matrículas, mantendo a integridade dos dados.

## Tecnologias Utilizadas
* **Linguagem:** Java
* **Persistência de Dados:** JPA
* **Operações de CRUD:** Implementação nativa via JDBC
* **Banco de Dados Relacional:** PostgreSQL

## Funcionalidades Implementadas
O sistema oferece as seguintes operações:

* **Cadastros:** Inserção de novos alunos, cursos e matrículas.
* **Listagens:** * Listagem de todos os alunos e cursos.
  * Listagem de todas as matrículas, exibindo os nomes do aluno e do curso.
* **Buscas Específicas:**
  * Busca de aluno pelo endereço de e-mail.
  * Busca de curso pelo nome.

## Modelagem de Dados
O sistema contém as seguintes entidades e atributos:

### Aluno
* `id` (Long)
* `nome` (String)
* `email` (String)
* `dataNascimento` (LocalDate)

### Curso
* `id` (Long)
* `nome` (String)
* `descricao` (String)
* `cargaHoraria` (int)

### Matrícula
* `id` (Long)
* `aluno` (Aluno)
* `curso` (Curso)
* `dataMatricula` (LocalDate)

## Como Executar o Projeto

1. Certifique-se de ter o **Java** e o **PostgreSQL** instalados em sua máquina.
2. Clone este repositório:
  ```bash
  git clone https://github.com/brunowasch/Sistema-Curso
  ```
3. Prepare o ambiente de acordo com o arquivo `.env.example`:
  ```
  DB_URL=
  DB_USER=
  DB_PASSWORD=
  ```
4. Execute a classe `SistemaControle` da aplicação através da sua IDE de preferência ou via terminal. As tabelas serão geradas automaticamente pelo JPA no banco de dados.

## Autor
<table> <tr> <td align="center"> <a href="https://github.com/brunowasch"> <img src="https://avatars.githubusercontent.com/brunowasch" width="100px;" alt="Bruno Waschburger Silva"/><br /> <sub><b>Bruno Waschburger Silva</b></sub> </a> <br /> 📧 bwaschburger@gmail.com </td> </tr> </table>

---
*Projeto desenvolvido como parte do Desafio Final do módulo 5 - 3035 Teach.*
