package io.metis.mitarbeiter.domain.gruppe;

import io.metis.common.domain.Repository;

import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
public interface GruppeRepository extends Repository<Gruppe, GruppeId> {
    Optional<Gruppe> findByName(String name);
}
