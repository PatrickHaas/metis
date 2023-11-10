package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MitarbeiterRestConfiguration {
    @Bean
    MitarbeiterRestMapper mitarbeiterRestMapper() {
        return new MitarbeiterRestMapper();
    }
}
