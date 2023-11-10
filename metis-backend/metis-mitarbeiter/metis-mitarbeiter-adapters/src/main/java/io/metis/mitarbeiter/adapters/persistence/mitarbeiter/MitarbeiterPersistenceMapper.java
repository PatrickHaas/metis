package io.metis.mitarbeiter.adapters.persistence.mitarbeiter;

import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
class MitarbeiterPersistenceMapper {

    private final MitarbeiterFactory mitarbeiterFactory;

    Mitarbeiter from(MitarbeiterEntity entity) {
        return mitarbeiterFactory.create(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), entity.getHiredOn(), entity.getEmailAddress(), entity.getJobTitle(), entity.getAssignedGroups());
    }

    MitarbeiterEntity to(Mitarbeiter mitarbeiter) {
        return MitarbeiterEntity.builder()
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
