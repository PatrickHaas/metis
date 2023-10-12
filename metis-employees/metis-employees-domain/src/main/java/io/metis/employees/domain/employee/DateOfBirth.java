package io.metis.employees.domain.employee;

import java.time.LocalDate;

public record DateOfBirth(LocalDate value) {
    public DateOfBirth {
        if (value == null) {
            throw new IllegalArgumentException("date of birth must not be null");
        } else if (!value.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("date of birth must be in the past");
        }
    }

    public static DateOfBirth of(int year, int month, int dayOfMonth) {
        return new DateOfBirth(LocalDate.of(year, month, dayOfMonth));
    }
}
