package io.metis.employees.domain.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class FirstNameTest {

    @ParameterizedTest
    @CsvSource(value = {
            ";first name must not be null or blank",
            " ;first name must not be null or blank",
            "    ;first name must not be null or blank",
            "a;first name must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;first name must be at most 60 characters long"
    }, delimiter = ';')
    void construction_shouldRaiseException_whenInvalid(String invalidFirstName, String expectedMessage) {
        Assertions.assertThatThrownBy(() -> new FirstName(invalidFirstName))
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
        assertThat(new FirstName(validFirstName)).isNotNull();
    }

}
