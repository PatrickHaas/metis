package io.metis.employees.domain.employee;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.regex.Pattern;

@ValueObject
public record EmailAddress(String value) {

    static final Pattern PATTERN = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public EmailAddress {
        if (value == null) {
            throw new IllegalArgumentException("email address must not be null");
        } else if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("email address must be valid");
        }
    }

}
