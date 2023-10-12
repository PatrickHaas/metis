package io.metis.employees.application.user;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventHandlerRegistry;
import io.metis.employees.application.employee.EmployeePrimaryPort;
import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.employee.EmployeeAssignedToGroup;
import io.metis.employees.domain.employee.EmployeeHired;
import io.metis.employees.domain.user.User;
import io.metis.employees.domain.user.UserRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public class UserPrimaryPort implements ApplicationService {

    private final io.metis.employees.domain.user.UserRepository repository;
    private final EmployeePrimaryPort employeePrimaryPort;

    public UserPrimaryPort(UserRepository repository, EmployeePrimaryPort employeePrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        this.repository = repository;
        this.employeePrimaryPort = employeePrimaryPort;
        eventHandlerRegistry.subscribe(EmployeeHired.class, this::createUser);
        eventHandlerRegistry.subscribe(EmployeeAssignedToGroup.class, this::assignRoleToUser);
    }

    void assignRoleToUser(EmployeeAssignedToGroup event) {
        repository.assignGroupByEmployeeId(event.employeeId(), event.groupId());
    }

    void createUser(EmployeeHired employeeHiredEvent) {
        Employee employee = this.employeePrimaryPort.findById(employeeHiredEvent.id());
        repository.save(new User(null, employeeHiredEvent.id(), employee.getFirstName().value(), employee.getLastName().value(), employee.getEmailAddress().value()));
    }

}
