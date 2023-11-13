package io.metis.personal.domain.gruppe;

import io.metis.common.domain.AggregateRoot;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
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
    private final Gruppenbeschreibung beschreibung;
    private final Set<Berechtigungsschluessel> zugewieseneBerechtigungen;
    private LocalDateTime initiiertAm;

    public Gruppe(GruppeId id, Gruppenname name, Gruppenbeschreibung beschreibung, LocalDateTime initiiertAm) {
        this(id, name, beschreibung, new HashSet<>(), initiiertAm);
    }

    public Gruppe(GruppeId id, Gruppenname name, Gruppenbeschreibung beschreibung) {
        this(id, name, beschreibung, new HashSet<>(), null);
    }

    public void initiate() {
        initiiertAm = LocalDateTime.now();
        domainEvents().add(new GruppeInitiiert(getId()));
    }

    public void weiseZu(Berechtigungsschluessel berechtigungsschluessel) {
        zugewieseneBerechtigungen.add(berechtigungsschluessel);
        domainEvents().add(new BerechtigungZugewiesen(getId(), berechtigungsschluessel));
    }
}
