package io.metis.employees.domain.permission;

import io.metis.common.domain.Repository;

import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
public interface PermissionRepository extends Repository<Permission, PermissionId> {
    Optional<Permission> findByKey(String key);
}
