package dev.dluks.rental.support;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public abstract class BaseIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.2-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected TransactionTemplate transactionTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.flyway.enabled", () -> "true");
    }

    protected void cleanDatabase() {
        transactionTemplate.execute(status -> {
            // Desabilita as foreign keys temporariamente
            entityManager.createNativeQuery("SET session_replication_role = 'replica'").executeUpdate();

            // Limpa as tabelas na ordem correta (das dependentes para as independentes)
            entityManager.createNativeQuery("TRUNCATE TABLE rentals CASCADE").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE customers CASCADE").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE agencies CASCADE").executeUpdate();
            entityManager.createNativeQuery("TRUNCATE TABLE vehicles CASCADE").executeUpdate();

            // Reabilita as foreign keys
            entityManager.createNativeQuery("SET session_replication_role = 'origin'").executeUpdate();

            return null;
        });
    }
}