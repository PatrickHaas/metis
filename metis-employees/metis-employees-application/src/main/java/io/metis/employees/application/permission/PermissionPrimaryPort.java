package io.metis.employees.application.permission;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.permission.Permission;
import io.metis.employees.domain.permission.PermissionId;
import io.metis.employees.domain.permission.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PrimaryPort
@RequiredArgsConstructor
public class PermissionPrimaryPort implements ApplicationService {
    private final PermissionRepository repository;
    private final EventPublisher eventPublisher;

    public Permission initiate(InitiatePermissionCommand command) {
        Optional<Permission> existingPermissionByKey = repository.findByKey(command.key());
        if (existingPermissionByKey.isPresent()) {
            throw new PermissionKeyAlreadyTakenException(command.key());
        }

        Permission permission = new Permission(new PermissionId(UUID.randomUUID()), command.key(), command.description());
        permission.initiate();
        return saveAndPublish(permission, repository, eventPublisher);
    }

    public List<Permission> findAll() {
        return repository.findAll();
    }

    public Permission findByKey(String key) {
        return repository.findByKey(key).orElseThrow(() -> new PermissionNotFoundException(key));
    }
}
