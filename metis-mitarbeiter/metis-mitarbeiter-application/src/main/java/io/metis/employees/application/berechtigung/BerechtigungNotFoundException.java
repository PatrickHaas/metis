package io.metis.employees.application.berechtigung;

public class BerechtigungNotFoundException extends RuntimeException {
    public BerechtigungNotFoundException(String key) {
        super("a permission with the key %s could not be found".formatted(key));
    }
}
