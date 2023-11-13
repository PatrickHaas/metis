package io.metis.personal.adapters.mitarbeiter.persistence;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.EmailAdresse;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
class InMemoryMitarbeiterPersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Mitarbeiter, MitarbeiterId> implements MitarbeiterRepository {
    @Override
    public Optional<Mitarbeiter> findByEmailAddress(EmailAdresse email) {
        return findAll().stream()
                .filter(mitarbeiter -> Objects.equals(email, mitarbeiter.getEmailAdresse()))
                .findFirst();
    }

    @Override
    public List<Mitarbeiter> findByGroupId(GruppeId gruppeId) {
        return findAll().stream()
                .filter(mitarbeiter -> mitarbeiter.getZugewieseneGruppen().contains(gruppeId))
                .toList();
    }
}
