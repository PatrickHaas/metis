package io.metis.mitarbeiter.application.benutzerkonto;

import io.metis.common.application.ApplicationService;
import io.metis.common.domain.EventHandlerRegistry;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.domain.benutzerkonto.Benutzerkonto;
import io.metis.mitarbeiter.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterEinerGruppeZugewiesen;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterEingestellt;

class BenutzerkontoService implements ApplicationService, BenutzerkontoPrimaryPort {

    private final BenutzerkontoRepository repository;
    private final MitarbeiterPrimaryPort mitarbeiterPrimaryPort;

    public BenutzerkontoService(BenutzerkontoRepository repository, MitarbeiterPrimaryPort mitarbeiterPrimaryPort, EventHandlerRegistry eventHandlerRegistry) {
        this.repository = repository;
        this.mitarbeiterPrimaryPort = mitarbeiterPrimaryPort;
        eventHandlerRegistry.subscribe(MitarbeiterEingestellt.class, this::erstelleBenutzerkonto);
        eventHandlerRegistry.subscribe(MitarbeiterEinerGruppeZugewiesen.class, this::weiseBenutzerRolleZu);
    }

    void weiseBenutzerRolleZu(MitarbeiterEinerGruppeZugewiesen event) {
        repository.assignGroupByEmployeeId(event.mitarbeiterId(), event.gruppeId());
    }

    void erstelleBenutzerkonto(MitarbeiterEingestellt mitarbeiterEingestelltEvent) {
        Mitarbeiter mitarbeiter = this.mitarbeiterPrimaryPort.getById(mitarbeiterEingestelltEvent.id());
        repository.save(new Benutzerkonto(null, mitarbeiterEingestelltEvent.id(), mitarbeiter.getVorname().value(), mitarbeiter.getNachname().value(), mitarbeiter.getEmailAdresse().value()));
    }

}
