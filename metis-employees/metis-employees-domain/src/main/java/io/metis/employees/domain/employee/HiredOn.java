package io.metis.employees.domain.employee;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDate;

@ValueObject
public record HiredOn(LocalDate value) {
    public HiredOn {
        if (value == null) {
            throw new IllegalArgumentException("hired on must not be null");
        }
    }

    public static HiredOn now() {
        return new HiredOn(LocalDate.now());
    }
}
