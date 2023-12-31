package io.metis.personal.application.gruppe;

import io.metis.common.domain.EventPublisher;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import io.metis.personal.domain.gruppe.Gruppe;
import io.metis.personal.domain.gruppe.GruppeFactory;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.gruppe.GruppeRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

@PrimaryPort
public interface GruppePrimaryPort {

    static GruppePrimaryPort create(GruppeRepository repository, EventPublisher eventPublisher) {
        return new GruppeService(repository, eventPublisher, new GruppeFactory());
    }

    Gruppe initiiere(InitiiereGruppeCommand command);

    void weiseBerechtigungZu(GruppeId gruppeId, Berechtigungsschluessel berechtigungsschluessel);

    List<Gruppe> findAll();

    Gruppe getById(GruppeId id);

    Optional<Gruppe> findByName(String name);
}
