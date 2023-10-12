package io.metis.employees.domain.employee;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.employee.EmployeeId;

public record EmployeeUpdated(EmployeeId id) implements DomainEvent {
}
