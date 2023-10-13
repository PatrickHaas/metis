package io.metis.mitarbeiter.domain.gruppe;

import io.metis.common.domain.DomainEvent;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungId;

@org.jmolecules.event.annotation.DomainEvent
public record BerechtigungZugewiesen(GruppeId gruppeId, BerechtigungId berechtigungId) implements DomainEvent {
}
