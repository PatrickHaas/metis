package io.metis.personal.adapters.mitarbeiter.persistence;

import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MitarbeiterPersistenceConfiguration {
    @Bean
    MitarbeiterPersistenceMapper mitarbeiterMapper(MitarbeiterFactory mitarbeiterFactory) {
        return new MitarbeiterPersistenceMapper(mitarbeiterFactory);
    }
}
