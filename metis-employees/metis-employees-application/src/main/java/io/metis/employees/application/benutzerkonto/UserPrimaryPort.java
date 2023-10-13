package io.metis.employees.application.benutzerkonto;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventHandlerRegistry;
import io.metis.employees.application.mitarbeiter.EmployeePrimaryPort;
import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.mitarbeiter.MitarbeiterEinerGruppeZugewiesen;
import io.metis.employees.domain.mitarbeiter.MitarbeiterEingestellt;
import io.metis.employees.domain.benutzerkonto.Benutzerkonto;
import io.metis.employees.domain.benutzerkonto.BenutzerkontoRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

@PrimaryPort
public class UserPrimaryPort implements ApplicationService {

    private final BenutzerkontoRepository repository;
    private final EmployeePrimaryPort employeePrimaryPort;

    public UserPrimaryPort(BenutzerkontoRepository repository, EmployeePrimaryPort employeePrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        this.repository = repository;
        this.employeePrimaryPort = employeePrimaryPort;
        eventHandlerRegistry.subscribe(MitarbeiterEingestellt.class, this::createUser);
        eventHandlerRegistry.subscribe(MitarbeiterEinerGruppeZugewiesen.class, this::assignRoleToUser);
    }

    void assignRoleToUser(MitarbeiterEinerGruppeZugewiesen event) {
        repository.assignGroupByEmployeeId(event.mitarbeiterId(), event.gruppeId());
    }

    void createUser(MitarbeiterEingestellt mitarbeiterEingestelltEvent) {
        Mitarbeiter mitarbeiter = this.employeePrimaryPort.findById(mitarbeiterEingestelltEvent.id());
        repository.save(new Benutzerkonto(null, mitarbeiterEingestelltEvent.id(), mitarbeiter.getVorname().value(), mitarbeiter.getNachname().value(), mitarbeiter.getEmailAdresse().value()));
    }

}
