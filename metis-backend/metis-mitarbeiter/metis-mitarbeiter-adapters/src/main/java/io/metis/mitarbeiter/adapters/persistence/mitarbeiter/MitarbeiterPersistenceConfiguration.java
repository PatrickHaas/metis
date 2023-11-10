package io.metis.mitarbeiter.adapters.persistence.mitarbeiter;

import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MitarbeiterPersistenceConfiguration {
    @Bean
    MitarbeiterMapper mitarbeiterMapper(MitarbeiterFactory mitarbeiterFactory) {
        return new MitarbeiterMapper(mitarbeiterFactory);
    }
}
