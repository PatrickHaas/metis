package io.metis.mitarbeiter.domain.mitarbeiter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VornameTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new Vorname(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("first name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsEmpty() {
        assertThatThrownBy(() -> new Vorname(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("first name must not be null or blank");
    }

    @Test
    void construction_shouldRaiseException_whenValueIsBlank() {
        assertThatThrownBy(() -> new Vorname("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("first name must not be null or blank");
    }

    @ParameterizedTest
    @CsvSource(value = {
            ";first name must not be null or blank",
            " ;first name must not be null or blank",
            "    ;first name must not be null or blank",
            "a;first name must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;first name must be at most 60 characters long"
    }, delimiter = ';')
    void construction_shouldRaiseException_whenInvalid(String invalidFirstName, String expectedMessage) {
        Assertions.assertThatThrownBy(() -> new Vorname(invalidFirstName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource({
            "Peter",
            "Tony",
            "Bruce",
            "aa",
            "BB",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff"
    })
    void constrution_shouldBeSuccessful_whenValueIsValue(String validFirstName) {
        assertThat(new Vorname(validFirstName)).isNotNull();
    }

}
