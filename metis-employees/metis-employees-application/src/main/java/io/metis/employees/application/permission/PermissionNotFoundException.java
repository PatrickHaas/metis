package io.metis.employees.application.permission;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String key) {
        super("a permission with the key %s could not be found".formatted(key));
    }
}
