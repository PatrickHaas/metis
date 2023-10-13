package io.metis.employees.application.benutzerkonto;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventHandlerRegistry;
import io.metis.employees.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.employees.domain.benutzerkonto.Benutzerkonto;
import io.metis.employees.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.mitarbeiter.MitarbeiterEinerGruppeZugewiesen;
import io.metis.employees.domain.mitarbeiter.MitarbeiterEingestellt;

class BenutzerkontoService implements ApplicationService, BenutzerkontoPrimaryPort {

    private final BenutzerkontoRepository repository;
    private final MitarbeiterPrimaryPort mitarbeiterPrimaryPort;

    public BenutzerkontoService(BenutzerkontoRepository repository, MitarbeiterPrimaryPort mitarbeiterPrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        this.repository = repository;
        this.mitarbeiterPrimaryPort = mitarbeiterPrimaryPort;
        eventHandlerRegistry.subscribe(MitarbeiterEingestellt.class, this::createUser);
        eventHandlerRegistry.subscribe(MitarbeiterEinerGruppeZugewiesen.class, this::assignRoleToUser);
    }

    void assignRoleToUser(MitarbeiterEinerGruppeZugewiesen event) {
        repository.assignGroupByEmployeeId(event.mitarbeiterId(), event.gruppeId());
    }

    void createUser(MitarbeiterEingestellt mitarbeiterEingestelltEvent) {
        Mitarbeiter mitarbeiter = this.mitarbeiterPrimaryPort.getById(mitarbeiterEingestelltEvent.id());
        repository.save(new Benutzerkonto(null, mitarbeiterEingestelltEvent.id(), mitarbeiter.getVorname().value(), mitarbeiter.getNachname().value(), mitarbeiter.getEmailAdresse().value()));
    }

}
