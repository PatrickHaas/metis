package io.metis.mitarbeiter.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;

public class MitarbeiterNotFoundException extends RuntimeException {
    public MitarbeiterNotFoundException(MitarbeiterId mitarbeiterId) {
        super("an employee with the id %s could not be found".formatted(mitarbeiterId.value()));
    }
}
