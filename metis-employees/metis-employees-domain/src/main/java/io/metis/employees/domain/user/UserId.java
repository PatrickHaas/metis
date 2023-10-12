package io.metis.employees.domain.user;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record UserId(UUID value) implements Identifier {
}
