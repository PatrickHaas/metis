package io.metis.employees.domain.employee;

import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;
import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Factory
public class EmployeeFactory {

    public Employee create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, String emailAddress, String jobTitle) {
        return create(id, firstName, lastName, dateOfBirth, null, emailAddress, jobTitle);
    }


    public Employee create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn, String emailAddress, String jobTitle) {
        return create(id, firstName, lastName, dateOfBirth, hiredOn, emailAddress, jobTitle, new HashSet<>());
    }

    public Employee create(UUID id, String firstName, String lastName, LocalDate dateOfBirth, LocalDate hiredOn, String emailAddress, String jobTitle, Set<UUID> assignedGroups) {
        return new Employee(new EmployeeId(id), new FirstName(firstName), new LastName(lastName), new DateOfBirth(dateOfBirth), Optional.ofNullable(hiredOn)
                .map(HiredOn::new)
                .orElse(null), new EmailAddress(emailAddress), jobTitle, assignedGroups.stream()
                .map(GroupId::new)
                .collect(Collectors.toSet()));
    }
}
