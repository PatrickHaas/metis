package io.metis.employees.application.group;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.group.Group;
import io.metis.employees.domain.group.GroupFactory;
import io.metis.employees.domain.group.GroupId;
import io.metis.employees.domain.group.GroupRepository;
import io.metis.employees.domain.permission.PermissionId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;

@PrimaryPort
@RequiredArgsConstructor
public class GroupPrimaryPort implements ApplicationService {
    private final GroupRepository repository;
    private final EventPublisher eventPublisher;
    private final GroupFactory factory;

    public Group initiate(InitiateGroupCommand command) {
        Optional<Group> existingGroupByName = repository.findByName(command.name());
        if (existingGroupByName.isPresent()) {
            throw new GroupNameAlreadyTakenException(command.name());
        }

        Group group = factory.create(command.name(), command.description());
        group.initiate();
        return saveAndPublish(group, repository, eventPublisher);
    }

    public List<Group> findAll() {
        return repository.findAll();
    }

    public Group findById(GroupId id) {
        return repository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
    }

    public void assignPermission(GroupId groupId, PermissionId permissionId) {
        Group group = findById(groupId);
        group.assignPermission(permissionId);
        saveAndPublish(group, repository, eventPublisher);
    }

    public Optional<Group> findByName(String name) {
        return repository.findByName(name);
    }
}
