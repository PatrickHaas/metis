package io.metis.employees.application.permission;

public class PermissionKeyAlreadyTakenException extends RuntimeException {
    public PermissionKeyAlreadyTakenException(String name) {
        super("A permission with key %s does already exist".formatted(name));
    }
}
