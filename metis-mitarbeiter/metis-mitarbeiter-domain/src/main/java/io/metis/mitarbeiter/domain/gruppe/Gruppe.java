package io.metis.mitarbeiter.domain.gruppe;

import io.metis.common.domain.AggregateRoot;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Gruppe extends AggregateRoot<GruppeId> {
    @Identity
    private final GruppeId id;
    private final Gruppenname name;
    private final Gruppenbeschreibung description;
    private final Set<BerechtigungId> assignedPermissions;
    private LocalDateTime initiatedAt;

    public Gruppe(GruppeId id, Gruppenname name, Gruppenbeschreibung description, LocalDateTime initiatedAt) {
        this(id, name, description, new HashSet<>(), initiatedAt);
    }

    public Gruppe(GruppeId id, Gruppenname name, Gruppenbeschreibung description) {
        this(id, name, description, new HashSet<>(), null);
    }

    public void initiate() {
        initiatedAt = LocalDateTime.now();
        domainEvents().add(new GruppeInitiiert(getId()));
    }

    public void assignPermission(BerechtigungId berechtigungId) {
        assignedPermissions.add(berechtigungId);
        domainEvents().add(new BerechtigungZugewiesen(getId(), berechtigungId));
    }
}
