package io.metis.personal.domain.benutzerkonto;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Benutzerkonto extends AggregateRoot<BenutzerkontoId> {
    @org.jmolecules.ddd.annotation.Identity
    private final BenutzerkontoId id;
    private final MitarbeiterId mitarbeiterId;
    private final String vorname;
    private final String nachname;
    private final String emailAdresse;
}
