package br.com.SistemaControle;

import br.com.SistemaControle.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class Sistema {
    public static void main(String[] args) {
        System.out.println("Iniciando BD");

        try {
            EntityManager em = JPAUtil.getEntityManager();

            System.out.println("\nConexão estabelecida com sucesso!");

            em.close();
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}