package io.metis.employees.application.group;

public class GroupNameAlreadyTakenException extends RuntimeException {
    public GroupNameAlreadyTakenException(String name) {
        super("A group with name %s does already exist".formatted(name));
    }
}
