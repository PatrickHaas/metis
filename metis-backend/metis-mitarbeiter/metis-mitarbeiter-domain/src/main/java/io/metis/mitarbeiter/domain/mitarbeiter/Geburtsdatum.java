package io.metis.mitarbeiter.domain.mitarbeiter;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record Geburtsdatum(LocalDate value) {
    public Geburtsdatum {
        if (value == null) {
            throw new IllegalArgumentException("date of birth must not be null");
        } else if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("date of birth must be in the past");
        }
    }

    public static Geburtsdatum of(int year, int month, int dayOfMonth) {
        return new Geburtsdatum(LocalDate.of(year, month, dayOfMonth));
    }
}
