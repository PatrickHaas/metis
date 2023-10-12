package io.metis.employees.domain.employee;

public record FirstName(String value) {
    public FirstName {
        if (value == null || value.trim().isBlank()) {
            throw new IllegalArgumentException("first name must not be null or blank");
        }
    }
}
