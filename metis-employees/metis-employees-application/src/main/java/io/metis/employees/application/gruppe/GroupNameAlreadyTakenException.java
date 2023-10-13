package io.metis.employees.application.gruppe;

public class GroupNameAlreadyTakenException extends RuntimeException {
    public GroupNameAlreadyTakenException(String name) {
        super("A group with name %s does already exist".formatted(name));
    }
}
