package io.metis.employees.domain.group;

import io.metis.common.domain.Repository;

import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
public interface GroupRepository extends Repository<Group, GroupId> {
    Optional<Group> findByName(String name);
}
