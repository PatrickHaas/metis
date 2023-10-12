package io.metis.employees.domain.employee;

import io.metis.common.domain.employee.EmployeeId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmployeeTest {

    @Test
    void hire_shouldSetHiredAtAndCreateDomainEvent() {
        EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
        Employee employee = new Employee(employeeId, new FirstName("Tony"), new LastName("Stark"), DateOfBirth.of(1980, 5, 28), new EmailAddress("tony@avengers.com"), "Icon-Man");
        employee.hire();
        assertThat(employee.getHiredOn().value()).isEqualTo(LocalDate.now());
        assertThat(employee.domainEvents()).containsExactly(new EmployeeHired(employeeId, employee.getHiredOn().value()));
    }

    @Test
    void update_shouldUpdateValuesAndCreateDomainEvent() {
        EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
        Employee employee = new Employee(employeeId, new FirstName("Tony"), new LastName("Stark"), DateOfBirth.of(1980, 5, 28), HiredOn.now(), new EmailAddress("tony@avengers.com"), "Iron-Man", new HashSet<>());
        employee.update(new FirstName("Peter"), new LastName("Parker"), DateOfBirth.of(2010, 8, 10), new EmailAddress("peter@avengers.com"), "Spiderman");
        assertThat(employee.getFirstName().value()).isEqualTo("Peter");
        assertThat(employee.getLastName().value()).isEqualTo("Parker");
        assertThat(employee.getDateOfBirth().value()).isEqualTo(LocalDate.of(2010, 8, 10));
        assertThat(employee.getEmailAddress().value()).isEqualTo("peter@avengers.com");
        assertThat(employee.getJobTitle()).isEqualTo("Spiderman");
        assertThat(employee.domainEvents()).containsExactly(new EmployeeUpdated(employeeId));
    }

}
