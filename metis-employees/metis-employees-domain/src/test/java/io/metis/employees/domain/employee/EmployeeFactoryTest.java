package io.metis.employees.domain.employee;

import io.metis.employees.domain.group.GroupId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeFactoryTest {

    @Test
    void create_shouldPassAllArguments() {
        UUID employeeId = UUID.randomUUID();
        UUID groupId = UUID.randomUUID();
        Employee employee = new EmployeeFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), LocalDate.now(), "tony@avengers.com", "Iron-Man", Set.of(groupId));
        assertThat(employee.getId().value()).isEqualTo(employeeId);
        assertThat(employee.getFirstName().value()).isEqualTo("Tony");
        assertThat(employee.getLastName().value()).isEqualTo("Stark");
        assertThat(employee.getDateOfBirth().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(employee.getHiredOn().value()).isEqualTo(LocalDate.now());
        assertThat(employee.getEmailAddress().value()).isEqualTo("tony@avengers.com");
        assertThat(employee.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(employee.getAssignedGroups()).containsExactly(new GroupId(groupId));
    }

    @Test
    void createWithoutHiredOnAndAssignedGroups_shouldSetHiredOnToNullAndInitAssignedGroups() {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new EmployeeFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        assertThat(employee.getId().value()).isEqualTo(employeeId);
        assertThat(employee.getFirstName().value()).isEqualTo("Tony");
        assertThat(employee.getLastName().value()).isEqualTo("Stark");
        assertThat(employee.getDateOfBirth().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(employee.getHiredOn()).isNull();
        assertThat(employee.getEmailAddress().value()).isEqualTo("tony@avengers.com");
        assertThat(employee.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(employee.getAssignedGroups()).isEmpty();
    }

    @Test
    void createWithoutAssignedGroups_shouldInitAssignedGroups() {
        UUID employeeId = UUID.randomUUID();
        Employee employee = new EmployeeFactory().create(employeeId, "Tony", "Stark", LocalDate.of(1970, 5, 29), LocalDate.now(), "tony@avengers.com", "Iron-Man");
        assertThat(employee.getId().value()).isEqualTo(employeeId);
        assertThat(employee.getFirstName().value()).isEqualTo("Tony");
        assertThat(employee.getLastName().value()).isEqualTo("Stark");
        assertThat(employee.getDateOfBirth().value()).isEqualTo(LocalDate.of(1970, 5, 29));
        assertThat(employee.getHiredOn().value()).isEqualTo(LocalDate.now());
        assertThat(employee.getEmailAddress().value()).isEqualTo("tony@avengers.com");
        assertThat(employee.getJobTitle()).isEqualTo("Iron-Man");
        assertThat(employee.getAssignedGroups()).isEmpty();
    }

}
