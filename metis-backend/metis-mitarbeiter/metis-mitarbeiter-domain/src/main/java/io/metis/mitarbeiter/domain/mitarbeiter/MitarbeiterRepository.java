package io.metis.mitarbeiter.domain.mitarbeiter;

import io.metis.common.domain.Repository;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;
import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
@SecondaryPort
public interface MitarbeiterRepository extends Repository<Mitarbeiter, MitarbeiterId> {
    Optional<Mitarbeiter> findByEmailAddress(EmailAdresse email);

    List<Mitarbeiter> findByGroupId(GruppeId gruppeId);
}
