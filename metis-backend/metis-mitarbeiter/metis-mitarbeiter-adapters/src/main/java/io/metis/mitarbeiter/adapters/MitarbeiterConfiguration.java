package io.metis.mitarbeiter.adapters;

import io.metis.common.adapters.CommonConfiguration;
import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.application.berechtigung.BerechtigungPrimaryPort;
import io.metis.mitarbeiter.application.gruppe.GruppePrimaryPort;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CommonConfiguration.class)
public class MitarbeiterConfiguration {
    @Bean
    MitarbeiterPrimaryPort mitarbeiterPrimaryPort(MitarbeiterRepository repository, EventPublisher eventPublisher) {
        return MitarbeiterPrimaryPort.create(repository, eventPublisher);
    }

    @Bean
    BerechtigungPrimaryPort berechtigungPrimaryPort(BerechtigungRepository repository, EventPublisher eventPublisher) {
        return BerechtigungPrimaryPort.create(repository, eventPublisher);
    }

    @Bean
    GruppePrimaryPort gruppePrimaryPort(GruppeRepository repository, EventPublisher eventPublisher) {
        return GruppePrimaryPort.create(repository, eventPublisher);
    }
}
