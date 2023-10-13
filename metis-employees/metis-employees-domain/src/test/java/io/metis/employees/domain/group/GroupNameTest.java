package io.metis.employees.domain.group;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupNameTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new GroupName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new GroupName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new GroupName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group name must not be null or blank");
    }

}
