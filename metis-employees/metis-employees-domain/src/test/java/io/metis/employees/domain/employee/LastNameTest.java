package io.metis.employees.domain.employee;

import io.metis.employees.domain.permission.PermissionKey;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LastNameTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new LastName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new LastName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new LastName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name must not be null or blank");
    }

    @ParameterizedTest
    @CsvSource(value = {
            ";last name must not be null or blank",
            " ;last name must not be null or blank",
            "    ;last name must not be null or blank",
            "a;last name must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;last name must be at most 60 characters long"
    }, delimiter = ';')
    void construction_shouldRaiseException_whenInvalid(String invalidLastName, String expectedMessage) {
        Assertions.assertThatThrownBy(() -> new LastName(invalidLastName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({
            "Parker",
            "Stark",
            "Banner",
            "aa",
            "BB",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff"
    })
    void constrution_shouldBeSuccessful_whenValueIsValue(String validLastName) {
        assertThat(new LastName(validLastName)).isNotNull();
    }

}
