package io.metis.employees.application.employee;

import java.time.LocalDate;

public record HireEmployeeCommand(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress,
                                  String jobTitle) {
}
