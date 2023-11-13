package io.metis.personal.application.mitarbeiter;

import java.time.LocalDate;

public record StelleMitarbeiterEinCommand(String vorname, String nachname, LocalDate geburtdatum, String emailadresse,
                                          String jobBeschreibung) {
}
