package io.metis.employees.domain.user;

import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface UserRepository {
    User save(User entity);

    void assignGroupByEmployeeId(EmployeeId employeeId, GroupId groupId);
}
