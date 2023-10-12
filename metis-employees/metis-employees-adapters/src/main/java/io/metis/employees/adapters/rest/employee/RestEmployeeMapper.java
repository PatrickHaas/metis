package io.metis.employees.adapters.rest.employee;

import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.employee.EmployeeFactory;
import io.metis.employees.domain.group.GroupId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RestEmployeeMapper {

    private final EmployeeFactory employeeFactory;

    Employee from(EmployeeMessage message) {
        return employeeFactory.create(message.id(), message.firstName(), message.lastName(), message.dateOfBirth(), message.hiredOn(), message.emailAddress(), message.jobTitle(), message.assignedGroups());
    }

    EmployeeMessage to(Employee employee) {
        return EmployeeMessage.builder()
                .id(employee.getId().value())
                .firstName(employee.getFirstName().value())
                .lastName(employee.getLastName().value())
                .dateOfBirth(employee.getDateOfBirth().value())
                .hiredOn(employee.getHiredOn().value())
                .emailAddress(employee.getEmailAddress().value())
                .jobTitle(employee.getJobTitle())
                .assignedGroups(employee.getAssignedGroups().stream()
                        .map(GroupId::value)
                        .collect(Collectors.toSet()))
                .build();
    }
}
