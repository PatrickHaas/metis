package io.metis.employees.domain.employee;

public record LastName(String value) {
    public LastName {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("last name must not be null or blank");
        }
    }
}
