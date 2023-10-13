package io.metis.employees.domain.mitarbeiter;

import io.metis.employees.domain.gruppe.GruppeId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MitarbeiterFactoryTest {

    @Test
    void create_shouldPassAllArguments() {
        UUID employeeId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        Mitarbeiter mitarbeiter = new MitarbeiterFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), LocalDate.now(), "tony@avengers.com", "Iron-Man", Set.of(groupId));
        assertThat(mitarbeiter.getId().value()).isEqualTo(employeeId);
        assertThat(mitarbeiter.getVorname().value()).isEqualTo("Tony");
        assertThat(mitarbeiter.getNachname().value()).isEqualTo("Stark");
        assertThat(mitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(mitarbeiter.getEinstelltAm().value()).isEqualTo(LocalDate.now());
        assertThat(mitarbeiter.getEmailAdresse().value()).isEqualTo("tony@avengers.com");
        assertThat(mitarbeiter.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(mitarbeiter.getAssignedGroups()).containsExactly(new GruppeId(groupId));
    }

    @Test
    void createWithoutHiredOnAndAssignedGroups_shouldSetHiredOnToNullAndInitAssignedGroups() {
        UUID employeeId = UUID.randomUUID();
        Mitarbeiter mitarbeiter = new MitarbeiterFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        assertThat(mitarbeiter.getId().value()).isEqualTo(employeeId);
        assertThat(mitarbeiter.getVorname().value()).isEqualTo("Tony");
        assertThat(mitarbeiter.getNachname().value()).isEqualTo("Stark");
        assertThat(mitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(mitarbeiter.getEinstelltAm()).isNull();
        assertThat(mitarbeiter.getEmailAdresse().value()).isEqualTo("tony@avengers.com");
        assertThat(mitarbeiter.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(mitarbeiter.getAssignedGroups()).isEmpty();
    }

    @Test
    void createWithoutAssignedGroups_shouldInitAssignedGroups() {
        UUID employeeId = UUID.randomUUID();
        Mitarbeiter mitarbeiter = new MitarbeiterFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), LocalDate.now(), "tony@avengers.com", "Iron-Man");
        assertThat(mitarbeiter.getId().value()).isEqualTo(employeeId);
        assertThat(mitarbeiter.getVorname().value()).isEqualTo("Tony");
        assertThat(mitarbeiter.getNachname().value()).isEqualTo("Stark");
        assertThat(mitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(mitarbeiter.getEinstelltAm().value()).isEqualTo(LocalDate.now());
        assertThat(mitarbeiter.getEmailAdresse().value()).isEqualTo("tony@avengers.com");
        assertThat(mitarbeiter.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(mitarbeiter.getAssignedGroups()).isEmpty();
    }

}
