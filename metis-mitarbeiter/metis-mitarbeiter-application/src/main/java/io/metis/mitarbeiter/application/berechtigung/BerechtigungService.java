package io.metis.mitarbeiter.application.berechtigung;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungFactory;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
class BerechtigungService implements ApplicationService, BerechtigungPrimaryPort {
    private final BerechtigungRepository repository;
    private final EventPublisher eventPublisher;
    private final BerechtigungFactory factory;

    @Override
    public Berechtigung initiiere(InitiiereBerechtigungCommand command) {
        Optional<Berechtigung> existingPermissionByKey = repository.findByKey(command.key());
        if (existingPermissionByKey.isPresent()) {
            throw new BerechtigungsschluesselAlreadyTakenException(command.key());
        }

        Berechtigung berechtigung = factory.create(command.key(), command.description());
        berechtigung.initiieren();
        return saveAndPublish(berechtigung, repository, eventPublisher);
    }

    @Override
    public List<Berechtigung> findAll() {
        return repository.findAll();
    }

    @Override
    public Berechtigung findByKey(String key) {
        return repository.findByKey(key).orElseThrow(() -> new BerechtigungNotFoundException(key));
    }
}
