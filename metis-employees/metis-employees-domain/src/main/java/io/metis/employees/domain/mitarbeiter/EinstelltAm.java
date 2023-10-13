package io.metis.employees.domain.mitarbeiter;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record EinstelltAm(LocalDate value) {
    public EinstelltAm {
        if (value == null) {
            throw new IllegalArgumentException("hired on must not be null");
        }
    }

    public static EinstelltAm now() {
        return new EinstelltAm(LocalDate.now());
    }
}
