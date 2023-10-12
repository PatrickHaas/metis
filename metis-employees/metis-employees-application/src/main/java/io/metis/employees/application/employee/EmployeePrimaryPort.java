package io.metis.employees.application.employee;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.employee.*;
import io.metis.employees.domain.group.GroupId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PrimaryPort
@RequiredArgsConstructor
public class EmployeePrimaryPort implements ApplicationService {

    private final EmployeeFactory employeeFactory;
    private final EmployeeRepository repository;
    private final EventPublisher eventPublisher;

    public Employee hire(HireEmployeeCommand command) {
        Employee employee = employeeFactory.create(UUID.randomUUID(), command.firstName(), command.lastName(), command.dateOfBirth(), command.emailAddress(), command.jobTitle());
        employee.hire();
        return saveAndPublish(employee, repository, eventPublisher);
    }

    public Employee assignToGroup(AssignToGroupCommand command) {
        Employee employee = findById(command.employeeId());
        employee.assignToGroup(command.groupId());
        return saveAndPublish(employee, repository, eventPublisher);
    }

    public Employee findById(EmployeeId employeeId) {
        return repository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public boolean existsById(EmployeeId employeeId) {
        return repository.existsById(employeeId);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee update(UpdateEmployeeCommand command) {
        Employee employee = findById(command.id());
        employee.update(new FirstName(command.firstName()), new LastName(command.lastName()), new DateOfBirth(command.dateOfBirth()), new EmailAddress(command.emailAddress()), command.jobTitle());
        return saveAndPublish(employee, repository, eventPublisher);
    }

    public void deleteById(EmployeeId employeeId) {
        repository.deleteById(employeeId);
    }

    public Optional<Employee> findByEmail(EmailAddress email) {
        return repository.findByEmailAddress(email);
    }

    public List<Employee> findByGroupId(GroupId groupId) {
        return repository.findByGroupId(groupId);
    }

    public Optional<Employee> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(new EmailAddress(emailAddress));
    }
}
