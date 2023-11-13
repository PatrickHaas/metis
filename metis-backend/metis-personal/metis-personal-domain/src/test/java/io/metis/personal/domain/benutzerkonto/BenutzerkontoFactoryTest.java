package io.metis.personal.domain.benutzerkonto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BenutzerkontoFactoryTest {

    @Test
    void create_shouldCreateNewUser() {
        UUID employeeId = UUID.randomUUID();
        Benutzerkonto user = new BenutzerkontoFactory().create(employeeId, "Tony", "Stark", "tony@avengers.com");

        assertThat(user.getId()).isNotNull();
        assertThat(user.getMitarbeiterId().value()).isEqualTo(employeeId);
        assertThat(user.getVorname()).isEqualTo("Tony");
        assertThat(user.getNachname()).isEqualTo("Stark");
        assertThat(user.getEmailAdresse()).isEqualTo("tony@avengers.com");
    }

}
