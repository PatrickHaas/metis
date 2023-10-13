package io.metis.employees.domain.gruppe;

import io.metis.common.domain.DomainEvent;

@org.jmolecules.event.annotation.DomainEvent
public record GruppeInitiiert(GruppeId id) implements DomainEvent {
}
