package io.metis.personal.application.berechtigung;

public class BerechtigungsschluesselAlreadyTakenException extends RuntimeException {
    public BerechtigungsschluesselAlreadyTakenException(String name) {
        super("A permission with key %s does already exist".formatted(name));
    }
}
