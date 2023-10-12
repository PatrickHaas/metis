package io.metis.employees.domain.employee;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.employee.EmployeeId;

import java.time.LocalDate;

public record EmployeeHired(EmployeeId id, LocalDate hiredOn) implements DomainEvent {
}
