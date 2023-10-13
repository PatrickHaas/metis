package io.metis.mitarbeiter.application.berechtigung;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungFactory;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BerechtigungServiceTest {

    @Mock
    private BerechtigungRepository repository;
    @Mock
    private EventPublisher eventPublisher;
    @Spy
    private BerechtigungFactory factory;

    @InjectMocks
    private BerechtigungService service;

    @Test
    void initiiere_wirftException_wennBerechtigungBereitsExistiert() {
        when(repository.findById(new Berechtigungsschluessel("key")))
                .thenReturn(Optional.of(new Berechtigung(new Berechtigungsschluessel("key"), null)));
        Assertions.assertThatThrownBy(() -> service.initiiere(new InitiiereBerechtigungCommand("key", "description")))
                .isInstanceOf(BerechtigungsschluesselAlreadyTakenException.class);
    }

}
