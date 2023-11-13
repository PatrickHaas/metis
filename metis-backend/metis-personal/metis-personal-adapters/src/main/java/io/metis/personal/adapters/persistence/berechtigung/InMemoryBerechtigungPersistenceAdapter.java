package io.metis.personal.adapters.persistence.berechtigung;

import io.metis.common.adapters.persistence.AbstractInMemoryPersistenceAdapter;
import io.metis.personal.domain.berechtigung.Berechtigung;
import io.metis.personal.domain.berechtigung.BerechtigungRepository;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;

class InMemoryBerechtigungPersistenceAdapter extends AbstractInMemoryPersistenceAdapter<Berechtigung, Berechtigungsschluessel> implements BerechtigungRepository {
}
