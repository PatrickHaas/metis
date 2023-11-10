package io.metis.mitarbeiter.adapters.rest.mitarbeiter;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record WeiseMitarbeiterEinerGruppeZuMessage(@NotNull UUID groupId) {
}
