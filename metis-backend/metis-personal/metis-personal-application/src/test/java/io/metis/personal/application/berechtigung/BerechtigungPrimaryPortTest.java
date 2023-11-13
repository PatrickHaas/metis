package io.metis.personal.application.berechtigung;

import io.metis.common.domain.EventPublisher;
import io.metis.personal.domain.berechtigung.BerechtigungRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class BerechtigungPrimaryPortTest {

    @Test
    void create() {
        BerechtigungRepository repository = Mockito.mock(BerechtigungRepository.class);
        EventPublisher eventPublisher = Mockito.mock(EventPublisher.class);
        BerechtigungPrimaryPort primaryPort = BerechtigungPrimaryPort.create(repository, eventPublisher);
        assertThat(primaryPort).isNotNull();
    }

}
