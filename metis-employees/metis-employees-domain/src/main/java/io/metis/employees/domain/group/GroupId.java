package io.metis.employees.domain.group;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record GroupId(UUID value) implements Identifier {
}
