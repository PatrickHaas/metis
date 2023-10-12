package io.metis.employees.domain.group;

import io.metis.common.domain.DomainEvent;
import io.metis.employees.domain.permission.PermissionId;

@org.jmolecules.event.annotation.DomainEvent
public record PermissionAssigned(GroupId groupId, PermissionId permissionId) implements DomainEvent {
}
