package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

record StelleMitarbeiterEinMessage(@NotBlank String vorname, @NotBlank String nachname,
                                   @NotNull LocalDate geburtsdatum, String emailAdresse, String jobTitel) {
}
