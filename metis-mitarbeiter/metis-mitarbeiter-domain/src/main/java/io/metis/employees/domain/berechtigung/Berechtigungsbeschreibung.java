package io.metis.employees.domain.berechtigung;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Berechtigungsbeschreibung(String value) {
    static final int MIN_LENGTH = 10;
    static final int MAX_LENGTH = 200;

    public Berechtigungsbeschreibung {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("permission description must not be null or blank");
        } else if (value.trim().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("permission description must be at least %s characters long".formatted(MIN_LENGTH));
        } else if (value.trim().length() > MAX_LENGTH) {
            throw new IllegalArgumentException("permission description must be at most %s characters long".formatted(MAX_LENGTH));
        }
    }
}
