package io.metis.employees.domain.group;

import io.metis.common.domain.DomainEvent;

public record GroupInitiated(GroupId id) implements DomainEvent {
}
