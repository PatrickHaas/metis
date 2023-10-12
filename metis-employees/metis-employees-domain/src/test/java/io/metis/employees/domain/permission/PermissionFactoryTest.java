package io.metis.employees.domain.permission;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PermissionFactoryTest {

    @ParameterizedTest
    @CsvSource(value = {
            ";permission key must not be null or blank",
            "   ;permission key must not be null or blank",
            "a;permission key must be at least 2 characters long",
            "aaaaaaaaaabbbbbbbbbbccccccccccddddddddddeeeeeeeeeeffffffffff1;permission key must be at most 60 characters long",
    }, delimiter = ';')
    void create_shouldRaiseException_whenPermissionKeyIsInvalid(String invalidPermissionKey, String expectedMessage) {
        PermissionFactory factory = new PermissionFactory();
        assertThatThrownBy(() -> factory.create(UUID.randomUUID(), invalidPermissionKey, "description", LocalDateTime.now()))
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
        PermissionFactory factory = new PermissionFactory();
        assertThatThrownBy(() -> factory.create(UUID.randomUUID(), "my:permission", invalidPermissionDescription, LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void create_shouldCreateAnIdAndPassAllParameters() {
        PermissionFactory factory = new PermissionFactory();
        Permission group = factory.create("permission:a", "Best permission there is");
        assertThat(group.getId()).isNotNull();
        assertThat(group.getKey().value()).isEqualTo("permission:a");
        assertThat(group.getDescription().value()).isEqualTo("Best permission there is");
        assertThat(group.getInitiatedAt()).isNull();
    }

    @Test
    void create_shouldPassAllParameters() {
        PermissionFactory factory = new PermissionFactory();
        UUID uuid = UUID.randomUUID();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Permission group = factory.create(uuid, "permission:a", "Best permission there is", initiatedAt);
        assertThat(group.getId().value()).isEqualTo(uuid);
        assertThat(group.getKey().value()).isEqualTo("permission:a");
        assertThat(group.getDescription().value()).isEqualTo("Best permission there is");
        assertThat(group.getInitiatedAt()).isEqualTo(initiatedAt);
    }
}
