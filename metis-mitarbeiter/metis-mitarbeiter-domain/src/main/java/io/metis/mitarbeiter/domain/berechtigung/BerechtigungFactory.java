package io.metis.mitarbeiter.domain.berechtigung;

import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDateTime;

@Factory
public class BerechtigungFactory {

    public Berechtigung create(String key, String description) {
        return new Berechtigung(new Berechtigungsschluessel(key), new Berechtigungsbeschreibung(description));
    }

    public Berechtigung create(String key, String description, LocalDateTime initiatedAt) {
        return new Berechtigung(new Berechtigungsschluessel(key), new Berechtigungsbeschreibung(description), initiatedAt);
    }

}
