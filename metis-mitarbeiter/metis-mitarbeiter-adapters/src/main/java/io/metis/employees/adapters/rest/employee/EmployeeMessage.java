package io.metis.employees.adapters.rest.employee;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
record EmployeeMessage(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn,
                       String emailAddress,
                       String jobTitle, Set<UUID> assignedGroups) {
}
