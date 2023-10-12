package io.metis.employees.domain.group;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GroupFactoryTest {

    @ParameterizedTest
    @CsvSource(value = {
            ";group name must not be null or blank",
            "   ;group name must not be null or blank",
            "a;group name must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;group name must be at most 60 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenGroupNameIsInvalid(String invalidGroupName, String expectedMessage) {
        GroupFactory factory = new GroupFactory();
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
        GroupFactory factory = new GroupFactory();
        assertThatThrownBy(() -> factory.create(UUID.randomUUID(), "My Group", invalidGroupDescription, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void create_shouldCreateAnIdAndPassAllParameters() {
        GroupFactory factory = new GroupFactory();
        Group group = factory.create("Group A", "Best group there is");
        assertThat(group.getId()).isNotNull();
        assertThat(group.getName().value()).isEqualTo("Group A");
        assertThat(group.getDescription().value()).isEqualTo("Best group there is");
        assertThat(group.getInitiatedAt()).isNull();
        assertThat(group.getAssignedPermissions()).isEmpty();
    }

    @Test
    void create_shouldPassAllParameters() {
        GroupFactory factory = new GroupFactory();
        UUID id = UUID.randomUUID();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Group group = factory.create(id, "Group A", "Best group there is", initiatedAt);
        assertThat(group.getId().value()).isEqualTo(id);
        assertThat(group.getName().value()).isEqualTo("Group A");
        assertThat(group.getDescription().value()).isEqualTo("Best group there is");
        assertThat(group.getInitiatedAt()).isEqualTo(initiatedAt);
        assertThat(group.getAssignedPermissions()).isEmpty();
    }
}
