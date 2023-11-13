package io.metis.personal.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.gruppe.GruppeId;

public record MitarbeiterEinerGruppeZuweisenCommand(GruppeId gruppeId, MitarbeiterId mitarbeiterId) {
}
