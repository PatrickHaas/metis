package io.metis.employees.application.gruppe;

import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.berechtigung.BerechtigungId;
import io.metis.employees.domain.gruppe.Gruppe;
import io.metis.employees.domain.gruppe.GruppeFactory;
import io.metis.employees.domain.gruppe.GruppeId;
import io.metis.employees.domain.gruppe.GruppeRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

@PrimaryPort
public interface GruppePrimaryPort {

    static GruppePrimaryPort create(GruppeRepository repository, EventPublisher eventPublisher) {
        return new GruppenService(repository, eventPublisher, new GruppeFactory());
    }

    Gruppe initiiere(InitiiereGruppeCommand command);

    void weiseBerechtigungZu(GruppeId gruppeId, BerechtigungId berechtigungId);

    List<Gruppe> findAll();

    Gruppe findById(GruppeId id);

    Optional<Gruppe> findByName(String name);
}
