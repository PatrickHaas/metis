package io.metis.employees.adapters.persistence.employee;

import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.employee.EmployeeFactory;
import io.metis.employees.domain.group.GroupId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class JpaEmployeeMapper {

    private final EmployeeFactory employeeFactory;

    Employee from(EmployeeEntity entity) {
        return employeeFactory.create(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getDateOfBirth(), entity.getHiredOn(), entity.getEmailAddress(), entity.getJobTitle(), entity.getAssignedGroups());
    }

    EmployeeEntity to(Employee employee) {
        return EmployeeEntity.builder()
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
