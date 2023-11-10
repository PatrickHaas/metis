package io.metis.mitarbeiter.domain.berechtigung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BerechtigungFactoryTest {

    @ParameterizedTest
    @CsvSource(value = {
            ";permission key must not be null or blank",
            "   ;permission key must not be null or blank",
            "a;permission key must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;permission key must be at most 60 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenPermissionKeyIsInvalid(String invalidPermissionKey, String expectedMessage) {
        BerechtigungFactory factory = new BerechtigungFactory();
        assertThatThrownBy(() -> factory.create(invalidPermissionKey, "description", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ";permission description must not be null or blank",
            "   ;permission description must not be null or blank",
            "a;permission description must be at least 10 characters long",
            "aaaaaaaaa;permission description must be at least 10 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjj1;permission description must be at most 200 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenGroupDescriptionIsInvalid(String invalidPermissionDescription, String expectedMessage) {
        BerechtigungFactory factory = new BerechtigungFactory();
        assertThatThrownBy(() -> factory.create("my:permission", invalidPermissionDescription, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void create_shouldCreateAnIdAndPassAllParameters() {
        BerechtigungFactory factory = new BerechtigungFactory();
        Berechtigung group = factory.create("permission:a", "Best permission there is");
        assertThat(group.getId()).isNotNull();
        assertThat(group.getSchluessel().value()).isEqualTo("permission:a");
        assertThat(group.getBeschreibung().value()).isEqualTo("Best permission there is");
        assertThat(group.getInitiiertAm()).isNull();
    }

    @Test
    void create_shouldPassAllParameters() {
        BerechtigungFactory factory = new BerechtigungFactory();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Berechtigung group = factory.create("permission:a", "Best permission there is", initiatedAt);
        assertThat(group.getSchluessel().value()).isEqualTo("permission:a");
        assertThat(group.getBeschreibung().value()).isEqualTo("Best permission there is");
        assertThat(group.getInitiiertAm()).isEqualTo(initiatedAt);
    }
}
