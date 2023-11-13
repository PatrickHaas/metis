package io.metis.personal.adapters.mitarbeiter.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MitarbeiterRestConfiguration {
    @Bean
    MitarbeiterRestMapper mitarbeiterRestMapper() {
        return new MitarbeiterRestMapper();
    }
}
