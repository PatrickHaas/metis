package io.metis.employees.domain.group;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupDescriptionTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new GroupDescription(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new GroupDescription(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new GroupDescription("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("group description must not be null or blank");
    }

}
