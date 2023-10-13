package io.metis.employees.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(MitarbeiterId mitarbeiterId) {
        super("an employee with the id %s could not be found".formatted(mitarbeiterId.value()));
    }
}
