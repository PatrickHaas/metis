package io.metis.mitarbeiter.application.berechtigung;

import io.metis.common.domain.EventPublisher;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungFactory;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;

@PrimaryPort
public interface BerechtigungPrimaryPort {

    static BerechtigungPrimaryPort create(BerechtigungRepository repository, EventPublisher eventPublisher) {
        return new BerechtigungService(repository, eventPublisher, new BerechtigungFactory());
    }

    Berechtigung initiiere(InitiiereBerechtigungCommand command);

    List<Berechtigung> findAll();

    Berechtigung findByKey(String key);
}
