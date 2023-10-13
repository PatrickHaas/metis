package io.metis.mitarbeiter.application.benutzerkonto;

import io.metis.common.domain.EventHandlerRegistry;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.benutzerkonto.BenutzerkontoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class BenutzerkontoPrimaryPortTest {

    @Test
    void create() {
        BenutzerkontoPrimaryPort primaryPort = BenutzerkontoPrimaryPort.create(Mockito.mock(BenutzerkontoRepository.class),
                Mockito.mock(MitarbeiterPrimaryPort.class),
                Mockito.mock(EventHandlerRegistry.class));
        assertThat(primaryPort).isNotNull();
    }

}
