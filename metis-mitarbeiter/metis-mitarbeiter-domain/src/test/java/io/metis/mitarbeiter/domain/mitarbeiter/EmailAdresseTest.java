package io.metis.mitarbeiter.domain.mitarbeiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailAdresseTest {

    @Test
    void construction_shouldRaiseException_whenValueIsNull() {
        assertThatThrownBy(() -> new EmailAdresse(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email address must not be null");
    }

    @ParameterizedTest
    @CsvSource({
            "@avengers.com",
            ".de"
    })
    void construction_shouldRaiseException_whenValueIsInvalid(String invalidEmailAddress) {
        assertThatThrownBy(() -> new EmailAdresse(invalidEmailAddress))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email address must be valid");
    }

    @ParameterizedTest
    @CsvSource({
            "tony@avengers.com",
            "1@2.org"
    })
    void construction_shouldConstructSuccessfully_whenValueIsValid(String validEmailAddress) {
        assertThat(new EmailAdresse(validEmailAddress)).isNotNull();
    }
}
