package dev.dluks.rental.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@Profile("test")
public class TestH2Configuration {
    // A configuração real fica no application-test.yml
}