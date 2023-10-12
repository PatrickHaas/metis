package io.metis.employees.adapters.persistence.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface SpringEmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    Optional<EmployeeEntity> findByEmailAddress(String email);

    List<EmployeeEntity> findByAssignedGroupsContaining(UUID assignedGroup);
}
