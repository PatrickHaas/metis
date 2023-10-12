package io.metis.employees.domain.permission;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record PermissionId(UUID value) implements Identifier {
}
