package br.com.SistemaControle.util;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JPAUtil {

    private static final EntityManagerFactory FACTORY;

    static {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();

        Map<String, String> props = new HashMap<>();
        props.put("jakarta.persistence.jdbc.url", dotenv.get("DB_URL"));
        props.put("jakarta.persistence.jdbc.user", dotenv.get("DB_USER"));
        props.put("jakarta.persistence.jdbc.password", dotenv.get("DB_PASSWORD"));

        FACTORY = Persistence.createEntityManagerFactory("SistemaControle", props);
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }
}