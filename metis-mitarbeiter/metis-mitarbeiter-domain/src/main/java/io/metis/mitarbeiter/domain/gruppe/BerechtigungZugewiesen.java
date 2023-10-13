package io.metis.mitarbeiter.domain.gruppe;

import io.metis.common.domain.DomainEvent;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;

@org.jmolecules.event.annotation.DomainEvent
public record BerechtigungZugewiesen(GruppeId gruppeId,
                                     Berechtigungsschluessel berechtigungsschluessel) implements DomainEvent {
}
