package io.metis.employees.application.employee;

import io.metis.common.domain.employee.EmployeeId;

import java.time.LocalDate;

public record UpdateEmployeeCommand(EmployeeId id, String firstName, String lastName, LocalDate dateOfBirth,
                                    String emailAddress, String jobTitle) {
}
