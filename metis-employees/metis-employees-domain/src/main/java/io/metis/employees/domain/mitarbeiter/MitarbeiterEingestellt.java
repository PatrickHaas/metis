package io.metis.employees.domain.mitarbeiter;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;

import java.time.LocalDate;

@org.jmolecules.event.annotation.DomainEvent
public record MitarbeiterEingestellt(MitarbeiterId id, LocalDate hiredOn) implements DomainEvent {
}
