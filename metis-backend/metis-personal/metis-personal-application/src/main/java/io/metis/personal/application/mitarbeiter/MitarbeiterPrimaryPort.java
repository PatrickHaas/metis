package io.metis.personal.application.mitarbeiter;

import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import io.metis.personal.domain.mitarbeiter.MitarbeiterRepository;

import java.util.List;
import java.util.Optional;

public interface MitarbeiterPrimaryPort {

    static MitarbeiterPrimaryPort create(MitarbeiterRepository repository, EventPublisher eventPublisher) {
        return new MitarbeiterService(new MitarbeiterFactory(), repository, eventPublisher);
    }

    Mitarbeiter stelleEin(StelleMitarbeiterEinCommand command);

    Mitarbeiter weiseGruppeZu(MitarbeiterEinerGruppeZuweisenCommand command);


    boolean existsById(MitarbeiterId mitarbeiterId);

    List<Mitarbeiter> findAll();

    Mitarbeiter getById(MitarbeiterId mitarbeiterId);

    Optional<Mitarbeiter> findByEmailAddress(String emailAddress);

    List<Mitarbeiter> findByGroupId(GruppeId gruppeId);

    void deleteById(MitarbeiterId mitarbeiterId);

    Mitarbeiter aktualisiereDaten(AktualisiereMitarbeiterdatenCommand command);


}
