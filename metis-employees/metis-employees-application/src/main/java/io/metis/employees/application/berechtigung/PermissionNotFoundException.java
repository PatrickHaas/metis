package io.metis.employees.application.berechtigung;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String key) {
        super("a permission with the key %s could not be found".formatted(key));
    }
}
