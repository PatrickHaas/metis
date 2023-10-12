package io.metis.employees.domain.permission;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PermissionTest {

    @Test
    void initiateShouldSetInitiatedAtAndCreateDomainEvent() {
        Permission permission = new Permission(new PermissionId(UUID.randomUUID()), new PermissionKey("key;a"), null);
        assertThat(permission.getKey().value()).isEqualTo("key;a");
        assertThat(permission.getDescription()).isNull();
        assertThat(permission.getInitiatedAt()).isNull();
        permission.initiate();
        assertThat(permission.getInitiatedAt()).isNotNull();
        assertThat(permission.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(permission.domainEvents()).containsExactly(new PermissionInitiated(permission.getId()));
    }
}
