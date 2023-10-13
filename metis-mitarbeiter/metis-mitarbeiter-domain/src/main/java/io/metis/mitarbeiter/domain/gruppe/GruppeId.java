package io.metis.mitarbeiter.domain.gruppe;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

public record GruppeId(UUID value) implements Identifier {
}
