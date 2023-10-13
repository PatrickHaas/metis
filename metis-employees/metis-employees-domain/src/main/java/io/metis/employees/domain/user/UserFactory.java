package io.metis.employees.domain.user;

import io.metis.common.domain.employee.EmployeeId;
import org.jmolecules.ddd.annotation.Factory;

import java.util.UUID;

@Factory
public class UserFactory {

    public User create(UUID employeeId, String firstName, String lastName, String emailAddress) {
        return new User(new UserId(UUID.randomUUID()), new EmployeeId(employeeId), firstName, lastName, emailAddress);
    }

}
