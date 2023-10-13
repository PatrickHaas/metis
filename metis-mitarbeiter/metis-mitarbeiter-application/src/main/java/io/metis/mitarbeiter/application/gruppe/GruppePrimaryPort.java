package io.metis.mitarbeiter.application.gruppe;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.gruppe.GruppeFactory;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;
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

    Gruppe findById(GruppeId id);

    Optional<Gruppe> findByName(String name);
}
