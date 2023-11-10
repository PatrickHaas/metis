package io.metis.mitarbeiter.adapters.persistence.gruppe;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;

import java.util.Objects;
import java.util.Optional;

class InMemoryGruppePersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Gruppe, GruppeId> implements GruppeRepository {
    @Override
    public Optional<Gruppe> findByName(String name) {
        return findAll().stream()
                .filter(gruppe -> Objects.equals(name, gruppe.getName().value()))
                .findFirst();
    }
}
