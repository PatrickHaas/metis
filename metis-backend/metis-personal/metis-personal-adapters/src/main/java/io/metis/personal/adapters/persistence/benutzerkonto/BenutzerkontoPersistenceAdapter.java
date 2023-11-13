package io.metis.personal.adapters.persistence.benutzerkonto;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.benutzerkonto.Benutzerkonto;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoId;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.personal.domain.gruppe.GruppeId;

class BenutzerkontoPersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Benutzerkonto, BenutzerkontoId> implements BenutzerkontoRepository {
    @Override
    public void assignGroupByEmployeeId(MitarbeiterId mitarbeiterId, GruppeId gruppeId) {
        // in-memory nicht benötigt
    }
}
