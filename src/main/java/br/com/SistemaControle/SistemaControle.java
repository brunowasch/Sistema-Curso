package br.com.SistemaControle;

import br.com.SistemaControle.model.Aluno;
import br.com.SistemaControle.model.Curso;
import br.com.SistemaControle.model.Matricula;
import jakarta.persistence.*;
import br.com.SistemaControle.util.JPAUtil;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class SistemaControle {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        em = JPAUtil.getEntityManager();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== Cursos: Sistema de Controle ===");
            System.out.println("1. Cadastrar Aluno");
            System.out.println("2. Cadastrar Curso");
            System.out.println("3. Matricular Aluno em Curso");
            System.out.println("4. Listar Alunos");
            System.out.println("5. Listar Cursos");
            System.out.println("6. Listar Matrículas");
            System.out.println("7. Buscar Aluno por E-mail");
            System.out.println("8. Buscar Curso por Nome");
            System.out.println("9. Relatório Avançado de Engajamento");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1 -> cadastrarAluno();
                    case 2 -> cadastrarCurso();
                    case 3 -> realizarMatricula();
                    case 4 -> listarAlunos();
                    case 5 -> listarCursos();
                    case 6 -> listarMatriculas();
                    case 7 -> buscarAlunoEmail();
                    case 8 -> buscarCursoNome();
                    case 9 -> relatorioEngajamento();
                    case 0 -> System.out.println("Encerrando sistema...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        em.close();
        emf.close();
    }

    private static void cadastrarAluno() {
        System.out.print("Nome do Aluno: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Data de Nascimento (dd/MM/yyyy): ");
        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine(), formatter);

        Aluno aluno = new Aluno(nome, email, dataNascimento);
        salvar(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void cadastrarCurso() {
        System.out.print("Nome do Curso: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Carga Horária (horas): ");
        int carga = Integer.parseInt(scanner.nextLine());

        Curso curso = new Curso(nome, descricao, carga);
        salvar(curso);
        System.out.println("Curso cadastrado com sucesso!");
    }

    private static void realizarMatricula() {
        listarAlunos();
        System.out.print("Digite o ID do Aluno: ");
        Long alunoId = Long.parseLong(scanner.nextLine());
        Aluno aluno = em.find(Aluno.class, alunoId);

        if (aluno == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        listarCursos();
        System.out.print("Digite o ID do Curso: ");
        Long cursoId = Long.parseLong(scanner.nextLine());
        Curso curso = em.find(Curso.class, cursoId);

        if (curso == null) {
            System.out.println("Curso não encontrado.");
            return;
        }

        Matricula matricula = new Matricula(aluno, curso, LocalDate.now());
        salvar(matricula);
        System.out.println("Matrícula realizada com sucesso na data de hoje!");
    }

    private static void listarAlunos() {
        List<Aluno> alunos = em.createQuery("SELECT a FROM Aluno a", Aluno.class).getResultList();
        System.out.println("\n--- Lista de Alunos ---");
        for (Aluno a : alunos) {
            System.out.println("ID: " + a.getId() + " | Nome: " + a.getNome() + " | E-mail: " + a.getEmail());
        }
    }

    private static void listarCursos() {
        List<Curso> cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        System.out.println("\n--- Lista de Cursos ---");
        for (Curso c : cursos) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Carga: " + c.getCargaHoraria() + "h");
        }
    }

    private static void listarMatriculas() {
        List<Matricula> matriculas = em.createQuery("SELECT m FROM Matricula m", Matricula.class).getResultList();
        System.out.println("\n--- Lista de Matrículas ---");
        for (Matricula m : matriculas) {
            System.out.println("ID Matrícula: " + m.getId() +
                    " | Aluno: " + m.getAluno().getNome() +
                    " | Curso: " + m.getCurso().getNome() +
                    " | Data: " + m.getDataMatricula().format(formatter));
        }
    }

    private static void buscarAlunoEmail() {
        System.out.print("Digite o e-mail exato do aluno: ");
        String email = scanner.nextLine();
        try {
            Aluno a = em.createQuery("SELECT a FROM Aluno a WHERE a.email = :email", Aluno.class)
                    .setParameter("email", email)
                    .getSingleResult();
            System.out.println("Aluno encontrado - Nome: " + a.getNome() + " | ID: " + a.getId());
        } catch (NoResultException e) {
            System.out.println("Nenhum aluno encontrado com este e-mail.");
        }
    }

    private static void buscarCursoNome() {
        System.out.print("Digite o nome ou parte do nome do curso: ");
        String nome = scanner.nextLine();
        List<Curso> cursos = em.createQuery("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(:nome)", Curso.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado.");
        } else {
            System.out.println("\n--- Resultados ---");
            for (Curso c : cursos) {
                System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome());
            }
        }
    }

    private static void relatorioEngajamento() {
        List<Curso> cursos = em.createQuery("SELECT c FROM Curso c", Curso.class).getResultList();
        LocalDate trintaDiasAtras = LocalDate.now().minusDays(30);

        System.out.println("\n=== Relatório Avançado de Engajamento ===");
        for (Curso c : cursos) {
            List<Matricula> matriculasDoCurso = em.createQuery("SELECT m FROM Matricula m WHERE m.curso = :curso", Matricula.class)
                    .setParameter("curso", c)
                    .getResultList();

            int totalAlunos = matriculasDoCurso.size();
            int somaIdades = 0;
            int matriculasRecentes = 0;

            for (Matricula m : matriculasDoCurso) {
                int idade = Period.between(m.getAluno().getDataNascimento(), LocalDate.now()).getYears();
                somaIdades += idade;

                if (!m.getDataMatricula().isBefore(trintaDiasAtras)) {
                    matriculasRecentes++;
                }
            }

            double mediaIdade = totalAlunos > 0 ? (double) somaIdades / totalAlunos : 0;

            System.out.println("Curso: " + c.getNome());
            System.out.println(" - Total de alunos matriculados: " + totalAlunos);
            System.out.printf(" - Média de idade: %.1f anos\n", mediaIdade);
            System.out.println(" - Matrículas nos últimos 30 dias: " + matriculasRecentes);
            System.out.println("-----------------------------------------");
        }
    }

    private static void salvar(Object entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }
}