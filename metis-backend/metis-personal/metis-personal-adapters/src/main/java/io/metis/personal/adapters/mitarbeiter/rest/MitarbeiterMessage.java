package io.metis.personal.adapters.mitarbeiter.rest;

import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
record MitarbeiterMessage(UUID id, String vorname, String nachname, LocalDate geburtsdatum, LocalDate eingestelltAm,
                          String emailadresse,
                          String jobBeschreibung, Set<UUID> zugewieseneGruppen) {

    static MitarbeiterMessage from(Mitarbeiter mitarbeiter) {
        return new MitarbeiterMessage(mitarbeiter.getId().value(), mitarbeiter.getVorname().value(), mitarbeiter.getNachname().value(), mitarbeiter.getGeburtsdatum().value(), mitarbeiter.getEinstelltAm().value(), mitarbeiter.getEmailAdresse().value(), mitarbeiter.getJobTitel(), mitarbeiter.getZugewieseneGruppen().stream()
                .map(GruppeId::value)
                .collect(Collectors.toSet()));
    }

}
