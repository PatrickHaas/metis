package io.metis.employees.domain.permission;

import io.metis.common.domain.DomainEvent;

public record PermissionInitiated(PermissionId id) implements DomainEvent {
}
