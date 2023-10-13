package io.metis.mitarbeiter.adapters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Diese Application bitte nicht entfernen, Spring braucht diese für das saubere Ausführen von Slice-Tests
public class MitarbeiterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MitarbeiterApplication.class, args);
    }
}
