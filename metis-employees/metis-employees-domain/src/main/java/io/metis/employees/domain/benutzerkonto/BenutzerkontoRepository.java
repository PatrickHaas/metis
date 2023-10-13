package io.metis.employees.domain.benutzerkonto;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.employees.domain.gruppe.GruppeId;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface BenutzerkontoRepository {
    Benutzerkonto save(Benutzerkonto entity);

    void assignGroupByEmployeeId(MitarbeiterId mitarbeiterId, GruppeId gruppeId);
}
