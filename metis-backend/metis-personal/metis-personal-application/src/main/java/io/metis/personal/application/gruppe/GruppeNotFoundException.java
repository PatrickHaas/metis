package io.metis.personal.application.gruppe;


import io.metis.personal.domain.gruppe.GruppeId;

public class GruppeNotFoundException extends RuntimeException {
    public GruppeNotFoundException(GruppeId id) {
        super("A group with the id %s could not be found".formatted(id.value()));
    }
}
