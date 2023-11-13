package io.metis.personal.adapters.mitarbeiter.rest;

import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class MitarbeiterRestMapper {

    MitarbeiterMessage to(Mitarbeiter mitarbeiter) {
        return MitarbeiterMessage.builder()
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
