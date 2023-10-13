package io.metis.mitarbeiter.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;

public record MitarbeiterEinerGruppeZuweisenCommand(GruppeId gruppeId, MitarbeiterId mitarbeiterId) {
}
