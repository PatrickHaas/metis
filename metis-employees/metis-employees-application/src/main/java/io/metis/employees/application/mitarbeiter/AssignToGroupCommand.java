package io.metis.employees.application.mitarbeiter;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.employees.domain.gruppe.GruppeId;

public record AssignToGroupCommand(GruppeId gruppeId, MitarbeiterId mitarbeiterId) {
}
