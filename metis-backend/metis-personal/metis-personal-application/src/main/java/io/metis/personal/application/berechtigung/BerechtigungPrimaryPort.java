package io.metis.personal.application.berechtigung;

import io.metis.common.domain.EventPublisher;
import io.metis.personal.domain.berechtigung.Berechtigung;
import io.metis.personal.domain.berechtigung.BerechtigungFactory;
import io.metis.personal.domain.berechtigung.BerechtigungRepository;
import org.jmolecules.architecture.hexagonal.PrimaryPort;

import java.util.List;

@PrimaryPort
public interface BerechtigungPrimaryPort {

    static BerechtigungPrimaryPort create(BerechtigungRepository repository, EventPublisher eventPublisher) {
        return new BerechtigungService(repository, eventPublisher, new BerechtigungFactory());
    }

    Berechtigung initiiere(InitiiereBerechtigungCommand command);

    List<Berechtigung> findAll();

    Berechtigung getByKey(String key);
}
