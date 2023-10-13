package io.metis.employees.domain.berechtigung;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record BerechtigungId(UUID value) implements Identifier {
}
