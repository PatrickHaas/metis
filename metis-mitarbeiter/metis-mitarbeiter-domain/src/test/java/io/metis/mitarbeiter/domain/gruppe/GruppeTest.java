package io.metis.mitarbeiter.domain.gruppe;

import io.metis.mitarbeiter.domain.berechtigung.BerechtigungId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GruppeTest {

    @Test
    void initiate_shouldSetInitiationDateAndCreateDomainEvent() {
        UUID groupId = UUID.randomUUID();
        Gruppe gruppe = new Gruppe(new GruppeId(groupId), new Gruppenname("Test group"), new Gruppenbeschreibung("Test description"));
        gruppe.initiate();

        assertThat(gruppe.getId().value()).isEqualTo(groupId);
        assertThat(gruppe.getName().value()).isEqualTo("Test group");
        assertThat(gruppe.getDescription().value()).isEqualTo("Test description");
        assertThat(gruppe.getInitiatedAt()).isNotNull();
        assertThat(gruppe.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(gruppe.domainEvents()).containsExactly(new GruppeInitiiert(gruppe.getId()));
    }

    @Test
    void assignPermission_shouldAddPermissionToAssignedOnesAndCreateDomainEvent() {
        UUID groupId = UUID.randomUUID();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Gruppe gruppe = new Gruppe(new GruppeId(groupId), new Gruppenname("Test group"), new Gruppenbeschreibung("Test description"), initiatedAt);
        assertThat(gruppe.getId().value()).isEqualTo(groupId);
        assertThat(gruppe.getName().value()).isEqualTo("Test group");
        assertThat(gruppe.getDescription().value()).isEqualTo("Test description");
        assertThat(gruppe.getInitiatedAt()).isEqualTo(initiatedAt);
        BerechtigungId berechtigungId = new BerechtigungId(UUID.randomUUID());
        gruppe.assignPermission(berechtigungId);
        assertThat(gruppe.domainEvents()).containsExactly(new BerechtigungZugewiesen(gruppe.getId(), berechtigungId));
        assertThat(gruppe.getAssignedPermissions()).containsExactly(berechtigungId);
    }

}
