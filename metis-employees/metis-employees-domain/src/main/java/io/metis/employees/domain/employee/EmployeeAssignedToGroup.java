package io.metis.employees.domain.employee;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;

public record EmployeeAssignedToGroup(EmployeeId employeeId, GroupId groupId) implements DomainEvent {
}
