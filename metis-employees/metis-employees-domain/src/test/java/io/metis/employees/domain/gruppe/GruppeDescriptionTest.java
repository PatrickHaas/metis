package io.metis.employees.domain.gruppe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GruppeDescriptionTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Gruppenbeschreibung(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Gruppenbeschreibung(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Gruppenbeschreibung("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

}
