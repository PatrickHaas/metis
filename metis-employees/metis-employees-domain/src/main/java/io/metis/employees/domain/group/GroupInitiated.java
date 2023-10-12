package io.metis.employees.domain.group;

import io.metis.common.domain.DomainEvent;

@org.jmolecules.event.annotation.DomainEvent
public record GroupInitiated(GroupId id) implements DomainEvent {
}
