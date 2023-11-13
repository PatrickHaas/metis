package io.metis.personal.domain.gruppe;

import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDateTime;
import java.util.UUID;

@Factory
public class GruppeFactory {


    public Gruppe create(String name, String description) {
        return new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname(name), new Gruppenbeschreibung(description));
    }

    public Gruppe create(UUID id, String name, String description, LocalDateTime initiatedAt) {
        return new Gruppe(new GruppeId(id), new Gruppenname(name), new Gruppenbeschreibung(description), initiatedAt);
    }

}
