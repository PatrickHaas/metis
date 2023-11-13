package io.metis.personal.domain.berechtigung;

import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Identifier;

@ValueObject
public record Berechtigungsschluessel(String value) implements Identifier {
    static final int MIN_LENGTH = 2;
    static final int MAX_LENGTH = 60;

    public Berechtigungsschluessel {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("permission key must not be null or blank");
        } else if (value.trim().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("permission key must be at least %s characters long".formatted(MIN_LENGTH));
        } else if (value.trim().length() > MAX_LENGTH) {
            throw new IllegalArgumentException("permission key must be at most %s characters long".formatted(MAX_LENGTH));
        }
    }
}
