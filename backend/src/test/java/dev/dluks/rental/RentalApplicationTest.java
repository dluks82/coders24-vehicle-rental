package dev.dluks.rental;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RentalApplicationTest {

    @Test
    void main() {
        RentalApplication.main(new String[]{});
    }
}