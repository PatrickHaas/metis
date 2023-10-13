package io.metis.employees.domain.berechtigung;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BerechtigungTest {

    @Test
    void initiateShouldSetInitiatedAtAndCreateDomainEvent() {
        Berechtigung berechtigung = new Berechtigung(new BerechtigungId(UUID.randomUUID()), new Berechtigungsschluessel("key;a"), null, null);
        assertThat(berechtigung.getKey().value()).isEqualTo("key;a");
        assertThat(berechtigung.getDescription()).isNull();
        assertThat(berechtigung.getInitiatedAt()).isNull();
        berechtigung.initiieren();
        assertThat(berechtigung.getInitiatedAt()).isNotNull();
        assertThat(berechtigung.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(berechtigung.domainEvents()).containsExactly(new BerechtigungInitiiert(berechtigung.getId()));
    }
}
