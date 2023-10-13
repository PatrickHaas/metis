package io.metis.employees.domain.gruppe;

import io.metis.common.domain.Repository;

import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
public interface GruppeRepository extends Repository<Gruppe, GruppeId> {
    Optional<Gruppe> findByName(String name);
}
