package io.metis.employees.application.mitarbeiter;

import java.time.LocalDate;

public record HireEmployeeCommand(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress,
                                  String jobTitle) {
}
