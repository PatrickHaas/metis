package io.metis.employees.domain.employee;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DateOfBirthTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new DateOfBirth(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("date of birth must not be null");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsInTheFuture() {
        assertThatThrownBy(() -> new DateOfBirth(LocalDate.now().plusDays(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("date of birth must be in the past");
    }

    @Test
    void factoryConstruction_shouldReturnTheCorrectValue() {
        assertThat(DateOfBirth.of(1977, 5, 16).value()).isEqualTo(LocalDate.of(1977, 5, 16));
    }


}
