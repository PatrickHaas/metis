package io.metis.employees.domain.permission;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PermissionKeyTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new PermissionKey(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new PermissionKey(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new PermissionKey("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("permission key must not be null or blank");
    }

}
