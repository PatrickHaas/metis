package io.metis.employees.domain.mitarbeiter;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;

@org.jmolecules.event.annotation.DomainEvent
public record MitarbeiterdatenAktualisiert(MitarbeiterId id) implements DomainEvent {
}
