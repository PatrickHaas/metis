package io.metis.employees.domain.berechtigung;

import io.metis.common.domain.Repository;

import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
public interface BerechtigungRepository extends Repository<Berechtigung, BerechtigungId> {
    Optional<Berechtigung> findByKey(String key);
}
