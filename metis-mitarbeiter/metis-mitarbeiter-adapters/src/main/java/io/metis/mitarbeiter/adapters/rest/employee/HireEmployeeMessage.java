package io.metis.mitarbeiter.adapters.rest.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

record HireEmployeeMessage(@NotBlank String firstName, @NotBlank String lastName,
                           @NotNull LocalDate dateOfBirth, String emailAddress, String jobTitle) {
}
