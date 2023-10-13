package io.metis.employees.application.gruppe;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.gruppe.Gruppe;
import io.metis.employees.domain.gruppe.GruppeFactory;
import io.metis.employees.domain.gruppe.GruppeId;
import io.metis.employees.domain.gruppe.GruppeRepository;
import io.metis.employees.domain.berechtigung.BerechtigungId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

@PrimaryPort
@RequiredArgsConstructor
public class GroupPrimaryPort implements ApplicationService {
    private final GruppeRepository repository;
    private final EventPublisher eventPublisher;
    private final GruppeFactory factory;

    public Gruppe initiate(InitiateGroupCommand command) {
        Optional<Gruppe> existingGroupByName = repository.findByName(command.name());
        if (existingGroupByName.isPresent()) {
            throw new GroupNameAlreadyTakenException(command.name());
        }

        Gruppe gruppe = factory.create(command.name(), command.description());
        gruppe.initiate();
        return saveAndPublish(gruppe, repository, eventPublisher);
    }

    public List<Gruppe> findAll() {
        return repository.findAll();
    }

    public Gruppe findById(GruppeId id) {
        return repository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
    }

    public void assignPermission(GruppeId gruppeId, BerechtigungId berechtigungId) {
        Gruppe gruppe = findById(gruppeId);
        gruppe.assignPermission(berechtigungId);
        saveAndPublish(gruppe, repository, eventPublisher);
    }

    public Optional<Gruppe> findByName(String name) {
        return repository.findByName(name);
    }
}
