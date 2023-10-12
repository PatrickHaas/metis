package io.metis.employees.domain.group;

import io.metis.employees.domain.permission.PermissionId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GroupTest {

    @Test
    void initiate_shouldSetInitiationDateAndCreateDomainEvent() {
        UUID groupId = UUID.randomUUID();
        Group group = new Group(new GroupId(groupId), "Test group", "Test description");
        group.initiate();

        assertThat(group.getId().value()).isEqualTo(groupId);
        assertThat(group.getName()).isEqualTo("Test group");
        assertThat(group.getDescription()).isEqualTo("Test description");
        assertThat(group.getInitiatedAt()).isNotNull();
        assertThat(group.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());
        assertThat(group.domainEvents()).containsExactly(new GroupInitiated(group.getId()));
    }

    @Test
    void assignPermission_shouldAddPermissionToAssignedOnesAndCreateDomainEvent() {
        UUID groupId = UUID.randomUUID();
        LocalDateTime initiatedAt = LocalDateTime.now();
        Group group = new Group(new GroupId(groupId), "Test group", "Test description", initiatedAt);
        assertThat(group.getId().value()).isEqualTo(groupId);
        assertThat(group.getName()).isEqualTo("Test group");
        assertThat(group.getDescription()).isEqualTo("Test description");
        assertThat(group.getInitiatedAt()).isEqualTo(initiatedAt);
        PermissionId permissionId = new PermissionId(UUID.randomUUID());
        group.assignPermission(permissionId);
        assertThat(group.domainEvents()).containsExactly(new PermissionAssigned(group.getId(), permissionId));
    }

}
