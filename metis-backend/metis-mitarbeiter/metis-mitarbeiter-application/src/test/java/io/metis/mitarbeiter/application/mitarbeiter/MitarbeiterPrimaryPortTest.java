package io.metis.mitarbeiter.application.mitarbeiter;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class MitarbeiterPrimaryPortTest {

    @Test
    void create() {
        MitarbeiterRepository repository = Mockito.mock(MitarbeiterRepository.class);
        EventPublisher eventPublisher = Mockito.mock(EventPublisher.class);
        MitarbeiterPrimaryPort primaryPort = MitarbeiterPrimaryPort.create(repository, eventPublisher);
        assertThat(primaryPort).isNotNull();
    }

}
