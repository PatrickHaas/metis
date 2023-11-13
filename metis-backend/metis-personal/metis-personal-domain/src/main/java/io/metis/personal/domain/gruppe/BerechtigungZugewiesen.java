package io.metis.personal.domain.gruppe;

import io.metis.common.domain.DomainEvent;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;

@org.jmolecules.event.annotation.DomainEvent
public record BerechtigungZugewiesen(GruppeId gruppeId,
                                     Berechtigungsschluessel berechtigungsschluessel) implements DomainEvent {
}
