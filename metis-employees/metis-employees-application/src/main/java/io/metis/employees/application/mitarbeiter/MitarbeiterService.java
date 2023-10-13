package io.metis.employees.application.mitarbeiter;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.employees.domain.gruppe.GruppeId;
import io.metis.employees.domain.mitarbeiter.*;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class MitarbeiterService implements ApplicationService, MitarbeiterPrimaryPort {

    private final MitarbeiterFactory factory;
    private final MitarbeiterRepository repository;
    private final EventPublisher eventPublisher;

    @Override
    public Mitarbeiter stelleEin(StelleMitarbeiterEinCommand command) {
        Mitarbeiter mitarbeiter = factory.create(UUID.randomUUID(), command.firstName(), command.lastName(), command.dateOfBirth(), command.emailAddress(), command.jobTitle());
        mitarbeiter.einstellen();
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    @Override
    public Mitarbeiter weiseGruppeZu(MitarbeiterEinerGruppeZuweisenCommand command) {
        Mitarbeiter mitarbeiter = getById(command.mitarbeiterId());
        mitarbeiter.zuweisen(command.gruppeId());
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    @Override
    public Mitarbeiter aktualisiereDaten(AktualisiereMitarbeiterdatenCommand command) {
        Mitarbeiter mitarbeiter = getById(command.id());
        mitarbeiter.update(new Vorname(command.firstName()), new Nachname(command.lastName()), new Geburtsdatum(command.dateOfBirth()), new EmailAdresse(command.emailAddress()), command.jobTitle());
        return saveAndPublish(mitarbeiter, repository, eventPublisher);
    }

    @Override
    public Mitarbeiter getById(MitarbeiterId mitarbeiterId) {
        return repository.findById(mitarbeiterId).orElseThrow(() -> new MitarbeiterNotFoundException(mitarbeiterId));
    }

    @Override
    public boolean existsById(MitarbeiterId mitarbeiterId) {
        return repository.existsById(mitarbeiterId);
    }

    @Override
    public List<Mitarbeiter> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(MitarbeiterId mitarbeiterId) {
        repository.deleteById(mitarbeiterId);
    }

    @Override
    public List<Mitarbeiter> findByGroupId(GruppeId gruppeId) {
        return repository.findByGroupId(gruppeId);
    }

    @Override
    public Optional<Mitarbeiter> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(new EmailAdresse(emailAddress));
    }
}
