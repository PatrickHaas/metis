package io.metis.mitarbeiter.domain.mitarbeiter;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;

@org.jmolecules.event.annotation.DomainEvent
public record MitarbeiterEinerGruppeZugewiesen(MitarbeiterId mitarbeiterId, GruppeId gruppeId) implements DomainEvent {
}
