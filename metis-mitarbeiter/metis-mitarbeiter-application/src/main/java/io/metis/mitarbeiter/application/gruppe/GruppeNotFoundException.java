package io.metis.mitarbeiter.application.gruppe;


import io.metis.mitarbeiter.domain.gruppe.GruppeId;

public class GruppeNotFoundException extends RuntimeException {
    public GruppeNotFoundException(GruppeId id) {
        super("A group with the id %s could not be found".formatted(id.value()));
    }

    public GruppeNotFoundException(String name) {
        super("A group with the name %s could not be found".formatted(name));
    }
}
