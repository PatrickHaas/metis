package io.metis.employees.domain.gruppe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GruppeNameTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Gruppenname(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Gruppenname(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Gruppenname("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

}
