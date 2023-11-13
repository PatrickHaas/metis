package io.metis.personal.adapters.rest.mitarbeiter;

import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class MitarbeiterRestMapper {

    MitarbeiterMessage to(Mitarbeiter mitarbeiter) {
        return MitarbeiterMessage.builder()
                .id(mitarbeiter.getId().value())
                .firstName(mitarbeiter.getVorname().value())
                .lastName(mitarbeiter.getNachname().value())
                .dateOfBirth(mitarbeiter.getGeburtsdatum().value())
                .hiredOn(mitarbeiter.getEinstelltAm().value())
                .emailAddress(mitarbeiter.getEmailAdresse().value())
                .jobTitle(mitarbeiter.getJobTitel())
                .assignedGroups(mitarbeiter.getZugewieseneGruppen().stream()
                        .map(GruppeId::value)
                        .collect(Collectors.toSet()))
                .build();
    }
}
