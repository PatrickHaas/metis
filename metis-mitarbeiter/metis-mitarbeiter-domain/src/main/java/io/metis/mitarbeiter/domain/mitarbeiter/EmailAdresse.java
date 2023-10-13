package io.metis.mitarbeiter.domain.mitarbeiter;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.regex.Pattern;

@ValueObject
public record EmailAdresse(String value) {

    static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    public EmailAdresse {
        if (value == null) {
            throw new IllegalArgumentException("email address must not be null");
        } else if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("email address must be valid");
        }
    }

}
