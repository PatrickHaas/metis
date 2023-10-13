package io.metis.employees.application.benutzerkonto;

import io.metis.common.domain.EventHandlerRegistry;
import io.metis.employees.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.employees.domain.benutzerkonto.BenutzerkontoRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public interface BenutzerkontoPrimaryPort {

    static BenutzerkontoPrimaryPort create(BenutzerkontoRepository repository, MitarbeiterPrimaryPort mitarbeiterPrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        return new BenutzerkontoService(repository, mitarbeiterPrimaryPort, eventHandlerRegistry);
    }

}
