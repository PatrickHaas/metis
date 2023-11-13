package io.metis.personal.domain.mitarbeiter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NachnameTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Nachname(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Nachname(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("last name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Nachname("   "))
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
        Assertions.assertThatThrownBy(() -> new Nachname(invalidLastName))
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
        assertThat(new Nachname(validLastName)).isNotNull();
    }

}
