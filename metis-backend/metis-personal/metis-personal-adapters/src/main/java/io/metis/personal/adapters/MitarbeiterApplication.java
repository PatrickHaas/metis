package io.metis.personal.adapters;

import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// Diese Application bitte nicht entfernen, Spring braucht diese für das saubere Ausführen von Slice-Tests
public class MitarbeiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MitarbeiterApplication.class, args);
    }

    @Bean
    MitarbeiterFactory mitarbeiterFactory() {
        return new MitarbeiterFactory();
    }
}
