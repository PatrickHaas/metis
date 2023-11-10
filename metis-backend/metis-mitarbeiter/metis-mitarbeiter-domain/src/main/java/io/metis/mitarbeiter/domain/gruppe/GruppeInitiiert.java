package io.metis.mitarbeiter.domain.gruppe;

import io.metis.common.domain.DomainEvent;

@org.jmolecules.event.annotation.DomainEvent
public record GruppeInitiiert(GruppeId id) implements DomainEvent {
}
