package io.metis.employees.domain.employee;

import io.metis.common.domain.Repository;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;
import org.jmolecules.architecture.hexagonal.SecondaryPort;

import java.util.List;
import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
@SecondaryPort
public interface EmployeeRepository extends Repository<Employee, EmployeeId> {
    Optional<Employee> findByEmailAddress(EmailAddress email);

    List<Employee> findByGroupId(GroupId groupId);
}
