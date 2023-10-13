package io.metis.employees.adapters.rest.employee;

import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.mitarbeiter.MitarbeiterFactory;
import io.metis.employees.domain.gruppe.GruppeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RestEmployeeMapper {

    private final MitarbeiterFactory mitarbeiterFactory;

    Mitarbeiter from(EmployeeMessage message) {
        return mitarbeiterFactory.create(message.id(), message.firstName(), message.lastName(), message.dateOfBirth(), message.hiredOn(), message.emailAddress(), message.jobTitle(), message.assignedGroups());
    }

    EmployeeMessage to(Mitarbeiter mitarbeiter) {
        return EmployeeMessage.builder()
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
