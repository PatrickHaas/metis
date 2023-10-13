package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MitarbeiterRestConfiguration {
    @Bean
    MitarbeiterPrimaryPort mitarbeiterPrimaryPort(MitarbeiterRepository repository, EventPublisher eventPublisher) {
        return MitarbeiterPrimaryPort.create(repository, eventPublisher);
    }

    @Bean
    MitarbeiterFactory mitarbeiterFactory() {
        return new MitarbeiterFactory();
    }

    @Bean
    MitarbeiterRestMapper mitarbeiterRestMapper() {
        return new MitarbeiterRestMapper();
    }
}
