package io.metis.mitarbeiter.application.gruppe;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungId;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.gruppe.GruppeFactory;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
class GruppenService implements ApplicationService, GruppePrimaryPort {
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
    public void weiseBerechtigungZu(GruppeId gruppeId, BerechtigungId berechtigungId) {
        Gruppe gruppe = findById(gruppeId);
        gruppe.assignPermission(berechtigungId);
        saveAndPublish(gruppe, repository, eventPublisher);
    }

    @Override
    public List<Gruppe> findAll() {
        return repository.findAll();
    }

    @Override
    public Gruppe findById(GruppeId id) {
        return repository.findById(id).orElseThrow(() -> new GruppeNotFoundException(id));
    }

    @Override
    public Optional<Gruppe> findByName(String name) {
        return repository.findByName(name);
    }
}
