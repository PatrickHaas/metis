package io.metis.employees.domain.gruppe;

import io.metis.common.domain.DomainEvent;
import io.metis.employees.domain.berechtigung.BerechtigungId;

@org.jmolecules.event.annotation.DomainEvent
public record BerechtigungZugewiesen(GruppeId gruppeId, BerechtigungId berechtigungId) implements DomainEvent {
}
