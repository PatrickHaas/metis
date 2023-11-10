package io.metis.mitarbeiter.adapters.persistence.berechtigung;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;

class InMemoryBerechtigungPersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Berechtigung, Berechtigungsschluessel> implements BerechtigungRepository {
}
