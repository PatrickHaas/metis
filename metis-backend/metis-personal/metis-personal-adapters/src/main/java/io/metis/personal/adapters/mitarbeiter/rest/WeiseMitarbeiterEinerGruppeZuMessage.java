package io.metis.personal.adapters.mitarbeiter.rest;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record WeiseMitarbeiterEinerGruppeZuMessage(@NotNull UUID groupId) {
}
