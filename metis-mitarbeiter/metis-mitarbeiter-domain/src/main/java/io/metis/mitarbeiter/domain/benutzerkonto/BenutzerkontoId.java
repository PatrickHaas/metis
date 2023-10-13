package io.metis.mitarbeiter.domain.benutzerkonto;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record BenutzerkontoId(UUID value) implements Identifier {
}
