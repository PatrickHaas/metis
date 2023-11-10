package io.metis.mitarbeiter.domain.berechtigung;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BerechtigungKeyTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Berechtigungsschluessel(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Berechtigungsschluessel(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Berechtigungsschluessel("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

}
