package io.metis.mitarbeiter.domain.berechtigung;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BerechtigungDescriptionTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Berechtigungsbeschreibung(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Berechtigungsbeschreibung(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Berechtigungsbeschreibung("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

}
