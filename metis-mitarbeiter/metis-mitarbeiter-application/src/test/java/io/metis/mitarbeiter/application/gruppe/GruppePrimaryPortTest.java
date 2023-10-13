package io.metis.mitarbeiter.application.gruppe;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class GruppePrimaryPortTest {

    @Test
    void create() {
        GruppeRepository repository = Mockito.mock(GruppeRepository.class);
        EventPublisher eventPublisher = Mockito.mock(EventPublisher.class);
        GruppePrimaryPort primaryPort = GruppePrimaryPort.create(repository, eventPublisher);
        assertThat(primaryPort).isNotNull();
    }

}
