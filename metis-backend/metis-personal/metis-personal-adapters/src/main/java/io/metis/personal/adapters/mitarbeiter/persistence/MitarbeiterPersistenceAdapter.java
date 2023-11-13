package io.metis.personal.adapters.mitarbeiter.persistence;


import io.metis.common.adapters.persistence.AbstractJpaPersistenceAdapter;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.EmailAdresse;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class MitarbeiterPersistenceAdapter extends AbstractJpaPersistenceAdapter<MitarbeiterSpringRepository, Mitarbeiter, MitarbeiterId, MitarbeiterEntity, UUID> implements MitarbeiterRepository {

    MitarbeiterPersistenceAdapter(MitarbeiterSpringRepository repository, MitarbeiterPersistenceMapper mapper) {
        super(repository, mapper::to, mapper::from, MitarbeiterId::value);
    }

    @Override
    public Optional<Mitarbeiter> findByEmailAddress(EmailAdresse email) {
        return getSpringRepository().findByEmailadresse(email.value())
                .map(getFromEntityMapper());
    }

    @Override
    public List<Mitarbeiter> findByGroupId(GruppeId gruppeId) {
        return getSpringRepository().findByZugewieseneGruppenContaining(gruppeId.value()).stream()
                .map(getFromEntityMapper())
                .toList();
    }
}
