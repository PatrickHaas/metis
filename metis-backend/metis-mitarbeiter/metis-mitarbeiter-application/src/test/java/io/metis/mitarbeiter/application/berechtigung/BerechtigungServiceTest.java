package io.metis.mitarbeiter.application.berechtigung;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungFactory;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
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
        assertThatThrownBy(() -> service.initiiere(new InitiiereBerechtigungCommand("key", "description")))
                .isInstanceOf(BerechtigungsschluesselAlreadyTakenException.class);
    }

    @Test
    void initiiere_initiiertSpeichertUndErzeugtEvent() {
        when(repository.findById(new Berechtigungsschluessel("key")))
                .thenReturn(Optional.empty());

        when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        Berechtigung berechtigung = service.initiiere(new InitiiereBerechtigungCommand("key", "Die Beschreibung dieser Berechtigung"));
        assertThat(berechtigung.getSchluessel().value()).isEqualTo("key");
        assertThat(berechtigung.getBeschreibung().value()).isEqualTo("Die Beschreibung dieser Berechtigung");
        assertThat(berechtigung.getInitiiertAm()).isNotNull();
        assertThat(berechtigung.getId()).isEqualTo(berechtigung.getSchluessel());
        for (DomainEvent domainEvent : berechtigung.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
        verify(factory).create("key", "Die Beschreibung dieser Berechtigung");
    }

    @Test
    void findAll() {
        Berechtigung berechtigung1 = factory.create("key-1", "Beschreibung, die es in sich hat");
        Berechtigung berechtigung2 = factory.create("key-2", "Beschreibung, die es in sich hat");
        when(repository.findAll()).thenReturn(List.of(
                berechtigung2,
                berechtigung1
        ));
        assertThat(service.findAll()).containsExactlyInAnyOrder(berechtigung1, berechtigung2);
    }

    @Test
    void getByKey_shouldRaiseException_whenPermissionDoesNotExist() {
        Berechtigungsschluessel berechtigungsschluessel = new Berechtigungsschluessel("key");
        when(repository.findById(berechtigungsschluessel)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getByKey("key"))
                .isInstanceOf(BerechtigungNotFoundException.class);
    }

    @Test
    void getByKey_shouldReturnExistingPermission() {
        Berechtigungsschluessel berechtigungsschluessel = new Berechtigungsschluessel("key");
        Berechtigung berechtigung1 = factory.create("key", "Beschreibung, die es in sich hat");
        when(repository.findById(berechtigungsschluessel)).thenReturn(Optional.of(berechtigung1));

        assertThat(service.getByKey("key")).isEqualTo(berechtigung1);
    }

}
