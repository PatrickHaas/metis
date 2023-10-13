package io.metis.mitarbeiter.domain.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MitarbeiterTest {

    @Test
    void hire_shouldSetHiredAtAndCreateDomainEvent() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter mitarbeiter = new Mitarbeiter(mitarbeiterId, new Vorname("Tony"), new Nachname("Stark"), Geburtsdatum.of(1980, 5, 28), new EmailAdresse("tony@avengers.com"), "Icon-Man");
        mitarbeiter.einstellen();
        assertThat(mitarbeiter.getEinstelltAm().value()).isEqualTo(LocalDate.now());
        assertThat(mitarbeiter.domainEvents()).containsExactly(new MitarbeiterEingestellt(mitarbeiterId, mitarbeiter.getEinstelltAm().value()));
    }

    @Test
    void update_shouldUpdateValuesAndCreateDomainEvent() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter mitarbeiter = new Mitarbeiter(mitarbeiterId, new Vorname("Tony"), new Nachname("Stark"), Geburtsdatum.of(1980, 5, 28), EinstelltAm.now(), new EmailAdresse("tony@avengers.com"), "Iron-Man", new HashSet<>());
        mitarbeiter.update(new Vorname("Peter"), new Nachname("Parker"), Geburtsdatum.of(2010, 8, 10), new EmailAdresse("peter@avengers.com"), "Spiderman");
        assertThat(mitarbeiter.getVorname().value()).isEqualTo("Peter");
        assertThat(mitarbeiter.getNachname().value()).isEqualTo("Parker");
        assertThat(mitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(2010, 8, 10));
        assertThat(mitarbeiter.getEmailAdresse().value()).isEqualTo("peter@avengers.com");
        assertThat(mitarbeiter.getJobTitel()).isEqualTo("Spiderman");
        assertThat(mitarbeiter.domainEvents()).containsExactly(new MitarbeiterdatenAktualisiert(mitarbeiterId));
    }

    @Test
    void assignToGroup_shouldAddGroupToAssignedOnesAndCreateDomainEvent() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        Mitarbeiter mitarbeiter = new Mitarbeiter(mitarbeiterId, new Vorname("Tony"), new Nachname("Stark"), Geburtsdatum.of(1980, 5, 28), EinstelltAm.now(), new EmailAdresse("tony@avengers.com"), "Iron-Man", new HashSet<>());
        GruppeId gruppeId = new GruppeId(UUID.randomUUID());
        mitarbeiter.zuweisen(gruppeId);
        assertThat(mitarbeiter.domainEvents()).containsExactly(new MitarbeiterEinerGruppeZugewiesen(mitarbeiterId, gruppeId));
    }

}
