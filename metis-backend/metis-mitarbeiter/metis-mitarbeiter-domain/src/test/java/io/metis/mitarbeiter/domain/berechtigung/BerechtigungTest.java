package io.metis.mitarbeiter.domain.berechtigung;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BerechtigungTest {

    @Test
    void initiateShouldSetInitiatedAtAndCreateDomainEvent() {
        Berechtigung berechtigung = new Berechtigung(new Berechtigungsschluessel("key;a"), null, null);
        assertThat(berechtigung.getSchluessel().value()).isEqualTo("key;a");
        assertThat(berechtigung.getBeschreibung()).isNull();
        assertThat(berechtigung.getInitiiertAm()).isNull();
        berechtigung.initiieren();
        assertThat(berechtigung.getInitiiertAm()).isNotNull();
        assertThat(berechtigung.getInitiiertAm().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(berechtigung.domainEvents()).containsExactly(new BerechtigungInitiiert(berechtigung.getId()));
    }
}
