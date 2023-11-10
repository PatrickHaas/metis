package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
record MitarbeiterMessage(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn,
                          String emailAddress,
                          String jobTitle, Set<UUID> assignedGroups) {

    static MitarbeiterMessage from(Mitarbeiter mitarbeiter) {
        return new MitarbeiterMessage(mitarbeiter.getId().value(), mitarbeiter.getVorname().value(), mitarbeiter.getNachname().value(), mitarbeiter.getGeburtsdatum().value(), mitarbeiter.getEinstelltAm().value(), mitarbeiter.getEmailAdresse().value(), mitarbeiter.getJobTitel(), mitarbeiter.getZugewieseneGruppen().stream()
                .map(GruppeId::value)
                .collect(Collectors.toSet()));
    }

}
