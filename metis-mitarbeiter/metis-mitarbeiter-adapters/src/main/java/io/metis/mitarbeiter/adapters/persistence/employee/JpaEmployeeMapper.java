package io.metis.mitarbeiter.adapters.persistence.employee;

import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class JpaEmployeeMapper {

    private final MitarbeiterFactory mitarbeiterFactory;

    Mitarbeiter from(EmployeeEntity entity) {
        return mitarbeiterFactory.create(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), entity.getHiredOn(), entity.getEmailAddress(), entity.getJobTitle(), entity.getAssignedGroups());
    }

    EmployeeEntity to(Mitarbeiter mitarbeiter) {
        return EmployeeEntity.builder()
                .id(mitarbeiter.getId().value())
                .firstName(mitarbeiter.getVorname().value())
                .lastName(mitarbeiter.getNachname().value())
                .dateOfBirth(mitarbeiter.getGeburtsdatum().value())
                .hiredOn(mitarbeiter.getEinstelltAm().value())
                .emailAddress(mitarbeiter.getEmailAdresse().value())
                .jobTitle(mitarbeiter.getJobTitle())
                .assignedGroups(mitarbeiter.getAssignedGroups().stream()
                        .map(GruppeId::value)
                        .collect(Collectors.toSet()))
                .build();
    }
}
