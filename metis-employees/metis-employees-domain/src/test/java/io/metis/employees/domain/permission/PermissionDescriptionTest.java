package io.metis.employees.domain.permission;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PermissionDescriptionTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new PermissionDescription(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new PermissionDescription(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new PermissionDescription("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission description must not be null or blank");
    }

}
