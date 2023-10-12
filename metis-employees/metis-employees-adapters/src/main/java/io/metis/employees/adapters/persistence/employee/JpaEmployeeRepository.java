package io.metis.employees.adapters.persistence.employee;


import io.metis.common.adapters.persistence.AbstractJpaRepository;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.employee.EmailAddress;
import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.employee.EmployeeRepository;
import io.metis.employees.domain.group.GroupId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class JpaEmployeeRepository extends AbstractJpaRepository<SpringEmployeeRepository, Employee, EmployeeId, EmployeeEntity, UUID> implements EmployeeRepository {

    public JpaEmployeeRepository(SpringEmployeeRepository repository, JpaEmployeeMapper mapper) {
        super(repository, mapper::to, mapper::from, EmployeeId::value);
    }

    @Override
    public Optional<Employee> findByEmailAddress(EmailAddress email) {
        return getSpringRepository().findByEmailAddress(email.value())
                .map(getFromEntityMapper());
    }

    @Override
    public List<Employee> findByGroupId(GroupId groupId) {
        return getSpringRepository().findByAssignedGroupsContaining(groupId.value()).stream()
                .map(getFromEntityMapper())
                .toList();
    }
}
