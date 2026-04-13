package br.com.SistemaControle;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Iniciando BD");
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaControle");
            EntityManager em = emf.createEntityManager();
            System.out.println("\nConexão estabelecida com sucesso!");
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}