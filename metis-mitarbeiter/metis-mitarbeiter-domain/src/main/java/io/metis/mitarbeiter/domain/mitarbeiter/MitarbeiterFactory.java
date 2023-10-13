package io.metis.mitarbeiter.domain.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Factory
public class MitarbeiterFactory {

    public Mitarbeiter create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String jobTitle) {
        return create(id, firstName, lastName, dateOfBirth, null, emailAddress, jobTitle);
    }


    public Mitarbeiter create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn, String emailAddress, String jobTitle) {
        return create(id, firstName, lastName, dateOfBirth, hiredOn, emailAddress, jobTitle, new HashSet<>());
    }

    public Mitarbeiter create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn, String emailAddress, String jobTitle, Set<UUID> assignedGroups) {
        return new Mitarbeiter(new MitarbeiterId(id), new Vorname(firstName), new Nachname(lastName), new Geburtsdatum(dateOfBirth), Optional.ofNullable(hiredOn)
                .map(EinstelltAm::new)
                .orElse(null), new EmailAdresse(emailAddress), jobTitle, assignedGroups.stream()
                .map(GruppeId::new)
                .collect(Collectors.toSet()));
    }
}
