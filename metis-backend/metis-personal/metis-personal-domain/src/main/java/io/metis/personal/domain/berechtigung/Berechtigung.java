package io.metis.personal.domain.berechtigung;

import io.metis.common.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Berechtigung extends AggregateRoot<Berechtigungsschluessel> {
    @Identity
    private final Berechtigungsschluessel schluessel;
    private final Berechtigungsbeschreibung beschreibung;
    private LocalDateTime initiiertAm;

    public Berechtigung(Berechtigungsschluessel schluessel, Berechtigungsbeschreibung beschreibung) {
        this.schluessel = schluessel;
        this.beschreibung = beschreibung;
    }

    public void initiieren() {
        initiiertAm = LocalDateTime.now();
        domainEvents().add(new BerechtigungInitiiert(getId()));
    }

    @Override
    public Berechtigungsschluessel getId() {
        return getSchluessel();
    }
}
