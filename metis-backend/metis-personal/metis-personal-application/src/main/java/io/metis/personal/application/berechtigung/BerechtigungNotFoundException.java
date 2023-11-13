package io.metis.personal.application.berechtigung;

public class BerechtigungNotFoundException extends RuntimeException {
    public BerechtigungNotFoundException(String key) {
        super("a permission with the key %s could not be found".formatted(key));
    }
}
