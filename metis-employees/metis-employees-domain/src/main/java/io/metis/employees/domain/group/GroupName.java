package io.metis.employees.domain.group;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record GroupName(String value) {
    static final int MIN_LENGTH = 2;
    static final int MAX_LENGTH = 60;

    public GroupName {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("group name must not be null or blank");
        } else if (value.trim().length() < MIN_LENGTH) {
            throw new IllegalArgumentException("group name must be at least %s characters long".formatted(MIN_LENGTH));
        } else if (value.trim().length() > MAX_LENGTH) {
            throw new IllegalArgumentException("group name must be at most %s characters long".formatted(MAX_LENGTH));
        }
    }
}
