package io.metis.personal.domain.gruppe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GruppeFactoryTest {

    @ParameterizedTest
    @CsvSource(value = {
            ";group name must not be null or blank",
            "   ;group name must not be null or blank",
            "a;group name must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;group name must be at most 60 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenGroupNameIsInvalid(String invalidGroupName, String expectedMessage) {
        GruppeFactory factory = new GruppeFactory();
        assertThatThrownBy(() -> factory.create(UUID.randomUUID(), invalidGroupName, "description", LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @ParameterizedTest
    @CsvSource(value = {
            ";group description must not be null or blank",
            "   ;group description must not be null or blank",
            "a;group description must be at least 10 characters long",
            "aaaaaaaaa;group description must be at least 10 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjjaaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffffgggggggggghhhhhhhhhhiiiiiiiiiijjjjjjjjjj1;group description must be at most 200 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenGroupDescriptionIsInvalid(String invalidGroupDescription, String expectedMessage) {
        GruppeFactory factory = new GruppeFactory();
        assertThatThrownBy(() -> factory.create(UUID.randomUUID(), "My Group", invalidGroupDescription, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void create_shouldCreateAnIdAndPassAllParameters() {
        GruppeFactory factory = new GruppeFactory();
        Gruppe gruppe = factory.create("Group A", "Best group there is");
        assertThat(gruppe.getId()).isNotNull();
        assertThat(gruppe.getName().value()).isEqualTo("Group A");
        assertThat(gruppe.getBeschreibung().value()).isEqualTo("Best group there is");
        assertThat(gruppe.getInitiiertAm()).isNull();
        assertThat(gruppe.getZugewieseneBerechtigungen()).isEmpty();
    }

    @Test
    void create_shouldPassAllParameters() {
        GruppeFactory factory = new GruppeFactory();
        UUID id = UUID.randomUUID();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Gruppe gruppe = factory.create(id, "Group A", "Best group there is", initiatedAt);
        assertThat(gruppe.getId().value()).isEqualTo(id);
        assertThat(gruppe.getName().value()).isEqualTo("Group A");
        assertThat(gruppe.getBeschreibung().value()).isEqualTo("Best group there is");
        assertThat(gruppe.getInitiiertAm()).isEqualTo(initiatedAt);
        assertThat(gruppe.getZugewieseneBerechtigungen()).isEmpty();
    }
}
