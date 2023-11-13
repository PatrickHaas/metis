package io.metis.personal.adapters.mitarbeiter.persistence;

import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class MitarbeiterPersistenceMapper {

    private final MitarbeiterFactory mitarbeiterFactory;

    Mitarbeiter from(MitarbeiterEntity entity) {
        return mitarbeiterFactory.create(entity.getId(), entity.getVorname(), entity.getNachname(), entity.getGeburtsdatum(), entity.getEingestelltAm(), entity.getEmailadresse(), entity.getJobBeschreibung(), entity.getZugewieseneGruppen());
    }

    MitarbeiterEntity to(Mitarbeiter mitarbeiter) {
        return MitarbeiterEntity.builder()
                .id(mitarbeiter.getId().value())
                .vorname(mitarbeiter.getVorname().value())
                .nachname(mitarbeiter.getNachname().value())
                .geburtsdatum(mitarbeiter.getGeburtsdatum().value())
                .eingestelltAm(mitarbeiter.getEinstelltAm().value())
                .emailadresse(mitarbeiter.getEmailAdresse().value())
                .jobBeschreibung(mitarbeiter.getJobTitel())
                .zugewieseneGruppen(mitarbeiter.getZugewieseneGruppen().stream()
                        .map(GruppeId::value)
                        .collect(Collectors.toSet()))
                .build();
    }
}
