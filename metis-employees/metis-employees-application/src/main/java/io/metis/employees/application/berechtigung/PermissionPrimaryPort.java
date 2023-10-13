package io.metis.employees.application.berechtigung;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.berechtigung.Berechtigung;
import io.metis.employees.domain.berechtigung.BerechtigungFactory;
import io.metis.employees.domain.berechtigung.BerechtigungRepository;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

@PrimaryPort
@RequiredArgsConstructor
public class PermissionPrimaryPort implements ApplicationService {
    private final BerechtigungRepository repository;
    private final EventPublisher eventPublisher;
    private final BerechtigungFactory factory;

    public Berechtigung initiate(InitiatePermissionCommand command) {
        Optional<Berechtigung> existingPermissionByKey = repository.findByKey(command.key());
        if (existingPermissionByKey.isPresent()) {
            throw new PermissionKeyAlreadyTakenException(command.key());
        }

        Berechtigung berechtigung = factory.create(command.key(), command.description());
        berechtigung.initiate();
        return saveAndPublish(berechtigung, repository, eventPublisher);
    }

    public List<Berechtigung> findAll() {
        return repository.findAll();
    }

    public Berechtigung findByKey(String key) {
        return repository.findByKey(key).orElseThrow(() -> new PermissionNotFoundException(key));
    }
}
