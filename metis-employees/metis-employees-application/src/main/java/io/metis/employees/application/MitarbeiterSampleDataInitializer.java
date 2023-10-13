package io.metis.employees.application;

import io.metis.common.application.SampleDataInitializer;
import io.metis.employees.application.mitarbeiter.AssignToGroupCommand;
import io.metis.employees.application.mitarbeiter.EmployeePrimaryPort;
import io.metis.employees.application.mitarbeiter.HireEmployeeCommand;
import io.metis.employees.application.gruppe.GroupPrimaryPort;
import io.metis.employees.application.gruppe.InitiateGroupCommand;
import io.metis.employees.application.berechtigung.PermissionPrimaryPort;
import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.gruppe.Gruppe;
import io.metis.employees.domain.berechtigung.Berechtigung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MitarbeiterSampleDataInitializer implements SampleDataInitializer {

    public static final String EMPLOYEE_GROUP_NAME = "employee";
    public static final String BACKOFFICE_GROUP_NAME = "backoffice";
    private static final String EMAIL_ERNST_EMPLOYEE = "ernst.employee@consultio.de";
    private static final String EMAIL_BERTA_EMPLOYEE = "berta.backoffice@consultio.de";

    private final GroupPrimaryPort groupPrimaryPort;
    private final EmployeePrimaryPort employeePrimaryPort;
    private final PermissionPrimaryPort permissionPrimaryPort;

    @Override
    public void initialize() {
        Gruppe employeeGruppe = groupPrimaryPort.findByName(EMPLOYEE_GROUP_NAME).orElse(null);
        if (employeeGruppe == null) {
            employeeGruppe = groupPrimaryPort.initiate(new InitiateGroupCommand(EMPLOYEE_GROUP_NAME, "a group containing all employees of the company"));
            log.debug("initiated group, name = {}, description = {}", employeeGruppe.getName(), employeeGruppe.getDescription());
        }

        Gruppe backofficeGruppe = getOrCreateBackofficeGroup();
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
        List<Berechtigung> missingBackofficeBerechtigungs = backofficePermissions.stream()
                .map(permissionPrimaryPort::findByKey)
                .filter(permission -> !backofficeGruppe.getAssignedPermissions().contains(permission.getId()))
                .toList();
        for (Berechtigung missingBackofficeBerechtigung : missingBackofficeBerechtigungs) {
            groupPrimaryPort.assignPermission(backofficeGruppe.getId(), missingBackofficeBerechtigung.getId());
            log.debug("assigned missing permission {} to group {}", missingBackofficeBerechtigung.getKey(), backofficeGruppe.getName());
        }

        Mitarbeiter ernst = employeePrimaryPort.findByEmailAddress(EMAIL_ERNST_EMPLOYEE).orElse(null);
        if (ernst == null) {
            ernst = employeePrimaryPort.hire(new HireEmployeeCommand("Ernst", "Employee", LocalDate.of(1970, 5, 29), EMAIL_ERNST_EMPLOYEE, "Consultant"));
            log.debug("created ernst employee, id = {}", ernst.getId().value());
        }

        if (!ernst.getAssignedGroups().contains(employeeGruppe.getId())) {
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(employeeGruppe.getId(), ernst.getId()));
            log.debug("assigned ernst employee to group {}", employeeGruppe.getName());
        }

        Mitarbeiter berta = employeePrimaryPort.findByEmailAddress(EMAIL_BERTA_EMPLOYEE).orElse(null);
        if (berta == null) {
            berta = employeePrimaryPort.hire(new HireEmployeeCommand("Berta", "Backoffice", LocalDate.of(1970, 5, 29), EMAIL_BERTA_EMPLOYEE, "Backoffice operator"));
            log.debug("created berta employee, id = {}", berta.getId().value());
        }

        if (!berta.getAssignedGroups().contains(backofficeGruppe.getId())) {
            employeePrimaryPort.assignToGroup(new AssignToGroupCommand(backofficeGruppe.getId(), berta.getId()));
            log.debug("assigned berta employee to group {}", backofficeGruppe.getName());
        }
    }

    private Gruppe getOrCreateBackofficeGroup() {
        Gruppe backofficeGruppe = groupPrimaryPort.findByName(BACKOFFICE_GROUP_NAME).orElse(null);
        if (backofficeGruppe == null) {
            backofficeGruppe = groupPrimaryPort.initiate(new InitiateGroupCommand(BACKOFFICE_GROUP_NAME, "a group containing all operative backoffice employees"));
        }
        return backofficeGruppe;
    }
}
