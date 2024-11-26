package dev.dluks.rental;

import dev.dluks.rental.config.TestContainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestContainersConfiguration.class)
class RentalApplicationIntegrationTests {

    @Test
    void contextLoads() {
    }
}