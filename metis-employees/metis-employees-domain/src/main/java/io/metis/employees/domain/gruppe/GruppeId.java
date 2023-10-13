package io.metis.employees.domain.gruppe;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record GruppeId(UUID value) implements Identifier {
}
