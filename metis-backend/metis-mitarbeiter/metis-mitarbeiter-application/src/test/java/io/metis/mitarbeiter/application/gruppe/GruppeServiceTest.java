package io.metis.mitarbeiter.application.gruppe;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import io.metis.mitarbeiter.domain.gruppe.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GruppeServiceTest {
    @Mock
    private GruppeRepository repository;
    @Mock
    private EventPublisher eventPublisher;
    @Spy
    private GruppeFactory factory;

    @InjectMocks
    private GruppeService service;

    @Test
    void initiate_shouldSaveAndPublish() {
        when(repository.findByName("CH-1")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Gruppe gruppe = service.initiiere(new InitiiereGruppeCommand("CH-1", "Chapter 1 is the best"));
        assertThat(gruppe.getId()).isNotNull();
        assertThat(gruppe.getName().value()).isEqualTo("CH-1");
        assertThat(gruppe.getBeschreibung().value()).isEqualTo("Chapter 1 is the best");
        assertThat(gruppe.getInitiiertAm().toLocalDate()).isEqualTo(LocalDate.now());

        verify(factory).create("CH-1", "Chapter 1 is the best");

        for (DomainEvent domainEvent : gruppe.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
    }

    @Test
    void initiate_shouldRaiseException_whenGroupNameIsAlreadyTaken() {
        when(repository.findByName("CH-1")).thenReturn(Optional.of(new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname("CH-1"), new Gruppenbeschreibung("Chapter 1 is the best"),
                LocalDateTime.now())));
        assertThatThrownBy(() -> service.initiiere(new InitiiereGruppeCommand("CH-1", "Chapter 1")))
                .isInstanceOf(GruppennameAlreadyTakenException.class);

        verifyNoInteractions(eventPublisher);
    }

    @Test
    void findByName() {
        Gruppe gruppe = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname("Chapter 1"), null, LocalDateTime.now());
        when(repository.findByName("Chapter 1")).thenReturn(Optional.of(gruppe));
        assertThat(service.findByName("Chapter 1")).contains(gruppe);
    }

    @Test
    void getById() {
        GruppeId gruppeId = new GruppeId(UUID.randomUUID());
        Gruppe gruppe = new Gruppe(gruppeId, new Gruppenname("Chapter 1"), null, LocalDateTime.now());
        when(repository.findById(gruppeId)).thenReturn(Optional.of(gruppe));
        assertThat(service.getById(gruppeId)).isEqualTo(gruppe);
    }

    @Test
    void getById_wirftException_wennKeineGruppeGefundenWurde() {
        GruppeId gruppeId = new GruppeId(UUID.randomUUID());
        when(repository.findById(gruppeId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(gruppeId))
                .isInstanceOf(GruppeNotFoundException.class);
    }

    @Test
    void findAll() {
        Gruppe chapter1 = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname("Chapter 1"), null, LocalDateTime.now());
        Gruppe chapter2 = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname("Chapter 2"), null, LocalDateTime.now());
        when(repository.findAll()).thenReturn(List.of(chapter2, chapter1));
        assertThat(service.findAll()).containsExactlyInAnyOrder(chapter1, chapter2);
    }

    @Test
    void assignPermission_shouldAssignPermissionToGroupSaveAndPublish() {
        GruppeId gruppeId = new GruppeId(UUID.randomUUID());
        Gruppe gruppe = new Gruppe(gruppeId, new Gruppenname("Chapter 1"), null, LocalDateTime.now());
        when(repository.findById(gruppeId)).thenReturn(Optional.of(gruppe));

        Berechtigungsschluessel berechtigungsschluessel = new Berechtigungsschluessel("schluessel");
        service.weiseBerechtigungZu(gruppeId, berechtigungsschluessel);

        assertThat(gruppe.getZugewieseneBerechtigungen()).containsExactly(berechtigungsschluessel);

        verify(repository).save(gruppe);
        for (DomainEvent domainEvent : gruppe.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
    }

}
