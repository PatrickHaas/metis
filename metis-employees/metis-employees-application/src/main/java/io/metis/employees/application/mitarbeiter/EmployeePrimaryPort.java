package io.metis.employees.application.mitarbeiter;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.employees.domain.mitarbeiter.*;
import io.metis.employees.domain.gruppe.GruppeId;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PrimaryPort
@RequiredArgsConstructor
public class EmployeePrimaryPort implements ApplicationService {

    private final MitarbeiterFactory mitarbeiterFactory;
    private final MitarbeiterRepository repository;
    private final EventPublisher eventPublisher;

    public Mitarbeiter hire(HireEmployeeCommand command) {
        Mitarbeiter mitarbeiter = mitarbeiterFactory.create(UUID.randomUUID(), command.firstName(), command.lastName(), command.dateOfBirth(), command.emailAddress(), command.jobTitle());
        mitarbeiter.hire();
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    public Mitarbeiter assignToGroup(AssignToGroupCommand command) {
        Mitarbeiter mitarbeiter = findById(command.mitarbeiterId());
        mitarbeiter.assignToGroup(command.gruppeId());
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    public Mitarbeiter findById(MitarbeiterId mitarbeiterId) {
        return repository.findById(mitarbeiterId).orElseThrow(() -> new EmployeeNotFoundException(mitarbeiterId));
    }

    public boolean existsById(MitarbeiterId mitarbeiterId) {
        return repository.existsById(mitarbeiterId);
    }

    public List<Mitarbeiter> findAll() {
        return repository.findAll();
    }

    public Mitarbeiter update(UpdateEmployeeCommand command) {
        Mitarbeiter mitarbeiter = findById(command.id());
        mitarbeiter.update(new Vorname(command.firstName()), new Nachname(command.lastName()), new Geburtsdatum(command.dateOfBirth()), new EmailAdresse(command.emailAddress()), command.jobTitle());
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    public void deleteById(MitarbeiterId mitarbeiterId) {
        repository.deleteById(mitarbeiterId);
    }

    public List<Mitarbeiter> findByGroupId(GruppeId gruppeId) {
        return repository.findByGroupId(gruppeId);
    }

    public Optional<Mitarbeiter> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(new EmailAdresse(emailAddress));
    }
}
