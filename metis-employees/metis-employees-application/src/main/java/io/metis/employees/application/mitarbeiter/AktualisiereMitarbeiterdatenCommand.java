package io.metis.employees.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;

import java.time.LocalDate;

public record AktualisiereMitarbeiterdatenCommand(MitarbeiterId id, String firstName, String lastName, LocalDate dateOfBirth,
                                                  String emailAddress, String jobTitle) {
}
