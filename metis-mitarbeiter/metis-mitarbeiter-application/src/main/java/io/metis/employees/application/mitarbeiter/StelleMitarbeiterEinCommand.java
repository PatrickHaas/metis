package io.metis.employees.application.mitarbeiter;

import java.time.LocalDate;

public record StelleMitarbeiterEinCommand(String firstName, String lastName, LocalDate dateOfBirth, String emailAddress,
                                          String jobTitle) {
}
