package io.metis.personal.adapters.mitarbeiter.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

record StelleMitarbeiterEinMessage(
        @NotBlank
        String vorname,
        @NotBlank
        String nachname,
        @NotNull
        LocalDate geburtsdatum,
        @NotNull
        @Email
        String emailAdresse,
        String jobTitel) {
}
