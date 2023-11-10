package io.metis.mitarbeiter.adapters.persistence.benutzerkonto;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.benutzerkonto.Benutzerkonto;
import io.metis.mitarbeiter.domain.benutzerkonto.BenutzerkontoId;
import io.metis.mitarbeiter.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import org.springframework.stereotype.Repository;

@Repository
class BenutzerkontoPersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Benutzerkonto, BenutzerkontoId> implements BenutzerkontoRepository {
    @Override
    public void assignGroupByEmployeeId(MitarbeiterId mitarbeiterId, GruppeId gruppeId) {
        // in-memory nicht benötigt
    }
}
