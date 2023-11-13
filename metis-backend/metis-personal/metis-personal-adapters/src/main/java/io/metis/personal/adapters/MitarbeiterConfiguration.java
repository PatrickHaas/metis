package io.metis.personal.adapters;

import io.metis.common.adapters.CommonConfiguration;
import io.metis.common.domain.EventHandlerRegistry;
import io.metis.common.domain.EventPublisher;
import io.metis.personal.application.benutzerkonto.BenutzerkontoPrimaryPort;
import io.metis.personal.application.berechtigung.BerechtigungPrimaryPort;
import io.metis.personal.application.gruppe.GruppePrimaryPort;
import io.metis.personal.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.personal.domain.berechtigung.BerechtigungRepository;
import io.metis.personal.domain.gruppe.GruppeRepository;
import io.metis.personal.domain.mitarbeiter.MitarbeiterRepository;
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

    @Bean
    BenutzerkontoPrimaryPort benutzerkontoPrimaryPort(BenutzerkontoRepository repository, MitarbeiterPrimaryPort mitarbeiterPrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        return BenutzerkontoPrimaryPort.create(repository, mitarbeiterPrimaryPort, eventHandlerRegistry);
    }
}
