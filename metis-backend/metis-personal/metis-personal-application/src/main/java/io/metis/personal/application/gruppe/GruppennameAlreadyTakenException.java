package io.metis.personal.application.gruppe;

public class GruppennameAlreadyTakenException extends RuntimeException {
    public GruppennameAlreadyTakenException(String name) {
        super("A group with name %s does already exist".formatted(name));
    }
}
