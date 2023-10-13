package io.metis.employees.domain.benutzerkonto;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record BenutzerkontoId(UUID value) implements Identifier {
}
