package io.metis.employees.application.employee;

import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.group.GroupId;

public record AssignToGroupCommand(GroupId groupId, EmployeeId employeeId) {
}
