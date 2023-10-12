package io.metis.employees.domain.permission;

import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDateTime;
import java.util.UUID;

@Factory
public class PermissionFactory {

    public Permission create(String key, String description) {
        return new Permission(new PermissionId(UUID.randomUUID()), new PermissionKey(key), new PermissionDescription(description));
    }

    public Permission create(UUID id, String key, String description, LocalDateTime initiatedAt) {
        return new Permission(new PermissionId(id), new PermissionKey(key), new PermissionDescription(description), initiatedAt);
    }

}
