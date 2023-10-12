package io.metis.employees.application.employee;

import io.metis.common.domain.employee.EmployeeId;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(EmployeeId employeeId) {
        super("an employee with the id %s could not be found".formatted(employeeId.value()));
    }
}
