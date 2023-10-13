package io.metis.employees.application;

import io.metis.common.application.ModuleInitializer;
import io.metis.employees.application.mitarbeiter.AssignToGroupCommand;
import io.metis.employees.application.mitarbeiter.EmployeePrimaryPort;
import io.metis.employees.application.mitarbeiter.HireEmployeeCommand;
import io.metis.employees.application.gruppe.GroupPrimaryPort;
import io.metis.employees.application.gruppe.InitiateGroupCommand;
import io.metis.employees.application.berechtigung.InitiatePermissionCommand;
import io.metis.employees.application.berechtigung.PermissionPrimaryPort;
import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.gruppe.Gruppe;
import io.metis.employees.domain.berechtigung.Berechtigung;
import io.metis.employees.domain.berechtigung.Berechtigungsschluessel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MitarbeiterModuleInitializer implements ModuleInitializer {

    public static final String ADMINISTRATION_GROUP_NAME = "administration";

    private final PermissionPrimaryPort permissionPrimaryPort;
    private final GroupPrimaryPort groupPrimaryPort;
    private final EmployeePrimaryPort employeePrimaryPort;

    @Override
    public void initialize() {
        List<Berechtigung> newBerechtigungs = getOrCreatePermissions();
        Gruppe administrationGruppe = getOrCreateAdministrationGroup(newBerechtigungs);
        if (employeePrimaryPort.findByEmailAddress("administrator@consultio.de").isEmpty()) {
            Mitarbeiter administrator = employeePrimaryPort.hire(new HireEmployeeCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@consultio.de", "Application administrator"));
            log.debug("created application administrator, id = {}", administrator.getId().value());
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(administrationGruppe.getId(), administrator.getId()));
            log.debug("assigned application administrator to administration group {}", administrationGruppe.getName());
        }
    }

    private List<Berechtigung> getOrCreatePermissions() {
        List<Berechtigung> existingBerechtigungs = permissionPrimaryPort.findAll();
        List<InitiatePermissionCommand> initiatePermissionCommands = List.of(
                new InitiatePermissionCommand("employees", ""),

                new InitiatePermissionCommand("employees:employees:list", ""),
                new InitiatePermissionCommand("employees:employees:show", ""),
                new InitiatePermissionCommand("employees:employees:hire", ""),
                new InitiatePermissionCommand("employees:employees:edit", ""),
                new InitiatePermissionCommand("employees:employees:delete", ""),
                new InitiatePermissionCommand("employees:employees:assigned-groups:show", ""),
                new InitiatePermissionCommand("employees:employees:assign-to-group", ""),

                new InitiatePermissionCommand("employees:groups:list", ""),
                new InitiatePermissionCommand("employees:groups:initiate", "")
        );

        List<String> existingPermissionKeys = existingBerechtigungs.stream()
                .map(Berechtigung::getKey)
                .map(Berechtigungsschluessel::value).toList();
        List<InitiatePermissionCommand> newInitiatePermissionCommands = initiatePermissionCommands.stream().filter(command -> !existingPermissionKeys.contains(command.key())).toList();
        List<Berechtigung> newBerechtigungs = new ArrayList<>();
        for (InitiatePermissionCommand newPermission : newInitiatePermissionCommands) {
            newBerechtigungs.add(permissionPrimaryPort.initiate(newPermission));
            log.debug("initiated permission, key = {}, description = {}", newPermission.key(), newPermission.description());
        }
        return newBerechtigungs;
    }

    private Gruppe getOrCreateAdministrationGroup(List<Berechtigung> newBerechtigungs) {
        Gruppe administrationGruppe = groupPrimaryPort.findByName(ADMINISTRATION_GROUP_NAME).orElse(null);
        if (administrationGruppe == null) {
            administrationGruppe = groupPrimaryPort.initiate(new InitiateGroupCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a consultio application"));
            log.debug("initiated group, name = {}, description = {}", administrationGruppe.getName(), administrationGruppe.getDescription());
        }
        for (Berechtigung newBerechtigung : newBerechtigungs) {
            groupPrimaryPort.assignPermission(administrationGruppe.getId(), newBerechtigung.getId());
            log.debug("assigned permission {} to group {}", newBerechtigung.getKey(), administrationGruppe.getName());
        }
        return administrationGruppe;
    }
}
