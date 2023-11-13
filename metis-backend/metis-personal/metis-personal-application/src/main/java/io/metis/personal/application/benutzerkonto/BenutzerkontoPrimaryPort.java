package io.metis.personal.application.benutzerkonto;

import io.metis.common.domain.EventHandlerRegistry;
import io.metis.personal.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface BenutzerkontoPrimaryPort {

    static BenutzerkontoPrimaryPort create(BenutzerkontoRepository repository, MitarbeiterPrimaryPort mitarbeiterPrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        return new BenutzerkontoService(repository, mitarbeiterPrimaryPort, eventHandlerRegistry);
    }

}
