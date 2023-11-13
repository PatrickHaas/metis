package io.metis.personal.domain.benutzerkonto;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import org.jmolecules.ddd.annotation.Factory;

import java.util.UUID;

@Factory
public class BenutzerkontoFactory {

    public Benutzerkonto create(UUID mitarbeiterId, String vorname, String nachname, String emailAdresse) {
        return new Benutzerkonto(new BenutzerkontoId(UUID.randomUUID()), new MitarbeiterId(mitarbeiterId), vorname, nachname, emailAdresse);
    }

}
