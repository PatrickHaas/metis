package io.metis.employees.domain.user;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.employee.EmployeeId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class User extends AggregateRoot<UserId> {
    @org.jmolecules.ddd.annotation.Identity
    private final UserId id;
    private final EmployeeId employeeId;
    private final String firstName;
    private final String lastName;
    private final String email;
}
