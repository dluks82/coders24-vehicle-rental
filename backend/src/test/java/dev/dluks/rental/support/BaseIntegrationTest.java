package dev.dluks.rental.support;

import dev.dluks.rental.config.TestContainersConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfiguration.class)
public abstract class BaseIntegrationTest {
    // Base para testes de integração
}