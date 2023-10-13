package io.metis.mitarbeiter.application.berechtigung;

public class BerechtigungNotFoundException extends RuntimeException {
    public BerechtigungNotFoundException(String key) {
        super("a permission with the key %s could not be found".formatted(key));
    }
}
