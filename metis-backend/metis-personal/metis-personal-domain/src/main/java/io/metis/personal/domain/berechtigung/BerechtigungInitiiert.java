package io.metis.personal.domain.berechtigung;

import io.metis.common.domain.DomainEvent;

@org.jmolecules.event.annotation.DomainEvent
public record BerechtigungInitiiert(Berechtigungsschluessel schluessel) implements DomainEvent {
}
