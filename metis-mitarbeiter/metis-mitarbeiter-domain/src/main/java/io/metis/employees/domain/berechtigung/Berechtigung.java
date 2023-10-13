package io.metis.employees.domain.berechtigung;

import io.metis.common.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Berechtigung extends AggregateRoot<BerechtigungId> {
    @Identity
    private final BerechtigungId id;
    private final Berechtigungsschluessel key;
    private final Berechtigungsbeschreibung description;
    private LocalDateTime initiatedAt;

    public Berechtigung(BerechtigungId id, Berechtigungsschluessel key, Berechtigungsbeschreibung description) {
        this.id = id;
        this.key = key;
        this.description = description;
    }

    public void initiieren() {
        initiatedAt = LocalDateTime.now();
        domainEvents().add(new BerechtigungInitiiert(getId()));
    }
}
