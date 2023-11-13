package io.metis.personal.application.gruppe;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import io.metis.personal.domain.gruppe.Gruppe;
import io.metis.personal.domain.gruppe.GruppeFactory;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.gruppe.GruppeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
class GruppeService implements ApplicationService, GruppePrimaryPort {
    private final GruppeRepository repository;
    private final EventPublisher eventPublisher;
    private final GruppeFactory factory;

    @Override
    public Gruppe initiiere(InitiiereGruppeCommand command) {
        Optional<Gruppe> existingGroupByName = repository.findByName(command.name());
        if (existingGroupByName.isPresent()) {
            throw new GruppennameAlreadyTakenException(command.name());
        }

        Gruppe gruppe = factory.create(command.name(), command.description());
        gruppe.initiate();
        return saveAndPublish(gruppe, repository, eventPublisher);
    }

    @Override
    public void weiseBerechtigungZu(GruppeId gruppeId, Berechtigungsschluessel berechtigungsschluessel) {
        Gruppe gruppe = getById(gruppeId);
        gruppe.weiseZu(berechtigungsschluessel);
        saveAndPublish(gruppe, repository, eventPublisher);
    }

    @Override
    public List<Gruppe> findAll() {
        return repository.findAll();
    }

    @Override
    public Gruppe getById(GruppeId id) {
        return repository.findById(id).orElseThrow(() -> new GruppeNotFoundException(id));
    }

    @Override
    public Optional<Gruppe> findByName(String name) {
        return repository.findByName(name);
    }
}
