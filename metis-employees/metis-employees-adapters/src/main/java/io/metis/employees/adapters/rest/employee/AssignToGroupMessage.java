package io.metis.employees.adapters.rest.employee;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

record AssignToGroupMessage(@NotNull UUID groupId) {
}
