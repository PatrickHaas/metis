package io.metis.employees.application;

import io.metis.common.application.SampleDataInitializer;
import io.metis.employees.application.employee.AssignToGroupCommand;
import io.metis.employees.application.employee.EmployeePrimaryPort;
import io.metis.employees.application.employee.HireEmployeeCommand;
import io.metis.employees.application.group.GroupPrimaryPort;
import io.metis.employees.application.group.InitiateGroupCommand;
import io.metis.employees.application.permission.PermissionPrimaryPort;
import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.group.Group;
import io.metis.employees.domain.permission.Permission;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class EmployeeSampleDataInitializer implements SampleDataInitializer {

    public static final String EMPLOYEE_GROUP_NAME = "employee";
    public static final String BACKOFFICE_GROUP_NAME = "backoffice";
    private static final String EMAIL_ERNST_EMPLOYEE = "ernst.employee@consultio.de";
    private static final String EMAIL_BERTA_EMPLOYEE = "berta.backoffice@consultio.de";

    private final GroupPrimaryPort groupPrimaryPort;
    private final EmployeePrimaryPort employeePrimaryPort;
    private final PermissionPrimaryPort permissionPrimaryPort;

    @Override
    public void initialize() {
        Group employeeGroup = groupPrimaryPort.findByName(EMPLOYEE_GROUP_NAME).orElse(null);
        if (employeeGroup == null) {
            employeeGroup = groupPrimaryPort.initiate(new InitiateGroupCommand(EMPLOYEE_GROUP_NAME, "a group containing all employees of the company"));
            log.debug("initiated group, name = {}, description = {}", employeeGroup.getName(), employeeGroup.getDescription());
        }

        Group backofficeGroup = getOrCreateBackofficeGroup();
        List<String> backofficePermissions = List.of(
                "employees",
                "employees:employees:list",
                "employees:employees:show",
                "employees:employees:hire",
                "employees:employees:edit",
                "employees:employees:assigned-groups:show",
                "employees:employees:assign-to-group",
                "employees:groups:list"
        );
        List<Permission> missingBackofficePermissions = backofficePermissions.stream()
                .map(permissionPrimaryPort::findByKey)
                .filter(permission -> !backofficeGroup.getAssignedPermissions().contains(permission.getId()))
                .toList();
        for (Permission missingBackofficePermission : missingBackofficePermissions) {
            groupPrimaryPort.assignPermission(backofficeGroup.getId(), missingBackofficePermission.getId());
            log.debug("assigned missing permission {} to group {}", missingBackofficePermission.getKey(), backofficeGroup.getName());
        }

        Employee ernst = employeePrimaryPort.findByEmailAddress(EMAIL_ERNST_EMPLOYEE).orElse(null);
        if (ernst == null) {
            ernst = employeePrimaryPort.hire(new HireEmployeeCommand("Ernst", "Employee", LocalDate.of(1970, 5, 29), EMAIL_ERNST_EMPLOYEE, "Consultant"));
            log.debug("created ernst employee, id = {}", ernst.getId().value());
        }

        if (!ernst.getAssignedGroups().contains(employeeGroup.getId())) {
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(employeeGroup.getId(), ernst.getId()));
            log.debug("assigned ernst employee to group {}", employeeGroup.getName());
        }

        Employee berta = employeePrimaryPort.findByEmailAddress(EMAIL_BERTA_EMPLOYEE).orElse(null);
        if (berta == null) {
            berta = employeePrimaryPort.hire(new HireEmployeeCommand("Berta", "Backoffice", LocalDate.of(1970, 5, 29), EMAIL_BERTA_EMPLOYEE, "Backoffice operator"));
            log.debug("created berta employee, id = {}", berta.getId().value());
        }

        if (!berta.getAssignedGroups().contains(backofficeGroup.getId())) {
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(backofficeGroup.getId(), berta.getId()));
            log.debug("assigned berta employee to group {}", backofficeGroup.getName());
        }
    }

    private Group getOrCreateBackofficeGroup() {
        Group backofficeGroup = groupPrimaryPort.findByName(BACKOFFICE_GROUP_NAME).orElse(null);
        if (backofficeGroup == null) {
            backofficeGroup = groupPrimaryPort.initiate(new InitiateGroupCommand(BACKOFFICE_GROUP_NAME, "a group containing all operative backoffice employees"));
        }
        return backofficeGroup;
    }
}
