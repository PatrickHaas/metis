package io.metis.employees.application;

import io.metis.common.application.ModuleInitializer;
import io.metis.employees.application.employee.AssignToGroupCommand;
import io.metis.employees.application.employee.EmployeePrimaryPort;
import io.metis.employees.application.employee.HireEmployeeCommand;
import io.metis.employees.application.group.GroupPrimaryPort;
import io.metis.employees.application.group.InitiateGroupCommand;
import io.metis.employees.application.permission.InitiatePermissionCommand;
import io.metis.employees.application.permission.PermissionPrimaryPort;
import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.group.Group;
import io.metis.employees.domain.permission.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EmployeeModuleInitializer implements ModuleInitializer {

    public static final String ADMINISTRATION_GROUP_NAME = "administration";

    private final PermissionPrimaryPort permissionPrimaryPort;
    private final GroupPrimaryPort groupPrimaryPort;
    private final EmployeePrimaryPort employeePrimaryPort;

    @Override
    public void initialize() {
        List<Permission> newPermissions = getOrCreatePermissions();
        Group administrationGroup = getOrCreateAdministrationGroup(newPermissions);
        if (employeePrimaryPort.findByEmailAddress("administrator@consultio.de").isEmpty()) {
            Employee administrator = employeePrimaryPort.hire(new HireEmployeeCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@consultio.de", "Application administrator"));
            log.debug("created application administrator, id = {}", administrator.getId().value());
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(administrationGroup.getId(), administrator.getId()));
            log.debug("assigned application administrator to administration group {}", administrationGroup.getName());
        }
    }

    private List<Permission> getOrCreatePermissions() {
        List<Permission> existingPermissions = permissionPrimaryPort.findAll();
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

        List<String> existingPermissionKeys = existingPermissions.stream().map(Permission::getKey).toList();
        List<InitiatePermissionCommand> newInitiatePermissionCommands = initiatePermissionCommands.stream().filter(command -> !existingPermissionKeys.contains(command.key())).toList();
        List<Permission> newPermissions = new ArrayList<>();
        for (InitiatePermissionCommand newPermission : newInitiatePermissionCommands) {
            newPermissions.add(permissionPrimaryPort.initiate(newPermission));
            log.debug("initiated permission, key = {}, description = {}", newPermission.key(), newPermission.description());
        }
        return newPermissions;
    }

    private Group getOrCreateAdministrationGroup(List<Permission> newPermissions) {
        Group administrationGroup = groupPrimaryPort.findByName(ADMINISTRATION_GROUP_NAME).orElse(null);
        if (administrationGroup == null) {
            administrationGroup = groupPrimaryPort.initiate(new InitiateGroupCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a consultio application"));
            log.debug("initiated group, name = {}, description = {}", administrationGroup.getName(), administrationGroup.getDescription());
        }
        for (Permission newPermission : newPermissions) {
            groupPrimaryPort.assignPermission(administrationGroup.getId(), newPermission.getId());
            log.debug("assigned permission {} to group {}", newPermission.getKey(), administrationGroup.getName());
        }
        return administrationGroup;
    }
}
