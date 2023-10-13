package io.metis.employees.application.employee;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.employee.EmployeeId;
import io.metis.employees.domain.employee.EmailAddress;
import io.metis.employees.domain.employee.Employee;
import io.metis.employees.domain.employee.EmployeeFactory;
import io.metis.employees.domain.employee.EmployeeRepository;
import io.metis.employees.domain.group.GroupId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeePrimaryPortTest {
    private final EmployeeFactory employeeFactory = new EmployeeFactory();
    @Mock
    private EmployeeRepository repository;
    @Mock
    private EventPublisher eventPublisher;

    private EmployeePrimaryPort primaryPort;

    @BeforeEach
    void createPrimaryPort() {
        primaryPort = new EmployeePrimaryPort(employeeFactory, repository, eventPublisher);
    }

    @Nested
    class HireEmployeeTests {
        @Test
        void hire_shouldHireAndSaveEmployee() {
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
            Employee hiredEmployee = primaryPort.hire(new HireEmployeeCommand("Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Engineer"));
            assertThat(hiredEmployee.getId()).isNotNull();
            assertThat(hiredEmployee.getFirstName().value()).isEqualTo("Tony");
            assertThat(hiredEmployee.getLastName().value()).isEqualTo("Stark");
            assertThat(hiredEmployee.getDateOfBirth().value()).isEqualTo(LocalDate.of(1980, 5, 28));
            assertThat(hiredEmployee.getJobTitle()).isEqualTo("Engineer");

            for (DomainEvent domainEvent : hiredEmployee.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }
    }

    @Nested
    class UpdateEmployeeTests {
        @Test
        void update_shouldUpdateAndSaveEmployee() {
            EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
            when(repository.findById(employeeId))
                    .thenReturn(Optional.of(employeeFactory.create(employeeId.value(), "Steve", "Rogers", LocalDate.of(1918, 7, 4), "steve@avengers.com", "Captain America")));
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
            Employee updatedEmployee = primaryPort.update(new UpdateEmployeeCommand(employeeId, "Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Iron-Man"));
            assertThat(updatedEmployee.getId()).isNotNull();
            assertThat(updatedEmployee.getFirstName().value()).isEqualTo("Tony");
            assertThat(updatedEmployee.getLastName().value()).isEqualTo("Stark");
            assertThat(updatedEmployee.getDateOfBirth().value()).isEqualTo(LocalDate.of(1980, 5, 28));
            assertThat(updatedEmployee.getEmailAddress().value()).isEqualTo("tony@avengers.com");
            assertThat(updatedEmployee.getJobTitle()).isEqualTo("Iron-Man");

            for (DomainEvent domainEvent : updatedEmployee.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }

        @Test
        void update_shouldRaiseEmployeeNotFoundException_whenEmployeeCouldNotBeFound() {
            EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
            when(repository.findById(employeeId))
                    .thenReturn(Optional.empty());
            assertThatThrownBy(() -> primaryPort.update(new UpdateEmployeeCommand(employeeId, "Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Engineer")))
                    .isInstanceOf(EmployeeNotFoundException.class);
            verifyNoInteractions(eventPublisher);
        }
    }

    @Nested
    class AssignToGroupTests {

        @Test
        void assignToGroup_shouldAssignSaveAndPublish() {
            EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
            when(repository.findById(employeeId)).thenReturn(Optional.of(employeeFactory.create(employeeId.value(), "Tony", "Stark", LocalDate.of(1970, 5, 28), "tony@avengers.com", "Iron-Man")));
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

            GroupId groupId = new GroupId(UUID.randomUUID());
            Employee updatedEmployee = primaryPort.assignToGroup(new AssignToGroupCommand(groupId, employeeId));
            assertThat(updatedEmployee.getAssignedGroups()).containsExactly(groupId);
            for (DomainEvent domainEvent : updatedEmployee.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }
    }

    @Test
    void existsById_shouldReturnTrue_whenEmployeeExists() {
        EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
        when(repository.existsById(employeeId))
                .thenReturn(true);
        assertThat(primaryPort.existsById(employeeId)).isTrue();
    }

    @Test
    void existsById_shouldReturnFalse_whenEmployeeDoesNotExist() {
        EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
        when(repository.existsById(employeeId))
                .thenReturn(false);
        assertThat(primaryPort.existsById(employeeId)).isFalse();
    }

    @Test
    void findAll_shouldReturnAllEmployees() {
        Employee tony = employeeFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        Employee bruce = employeeFactory.create(UUID.randomUUID(), "Bruce", "Banner", LocalDate.of(1970, 5, 29), "bruce@avengers.com", "Hulk");
        when(repository.findAll()).thenReturn(List.of(tony, bruce));
        assertThat(primaryPort.findAll()).containsExactlyInAnyOrder(tony, bruce);
    }

    @Test
    void deleteById_shouldDeleteById() {
        EmployeeId employeeId = new EmployeeId(UUID.randomUUID());
        doNothing().when(repository).deleteById(employeeId);
        primaryPort.deleteById(employeeId);
        verify(repository).deleteById(employeeId);
    }

    @Test
    void findByGroupId_shouldFindAllEmployees_assignedToASpecificGroup() {
        Employee tony = employeeFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        Employee bruce = employeeFactory.create(UUID.randomUUID(), "Bruce", "Banner", LocalDate.of(1970, 5, 29), "bruce@avengers.com", "Hulk");
        GroupId groupId = new GroupId(UUID.randomUUID());
        when(repository.findByGroupId(groupId)).thenReturn(List.of(tony, bruce));
        assertThat(primaryPort.findByGroupId(groupId)).containsExactlyInAnyOrder(tony, bruce);
    }

    @Test
    void findByEmailAddress_shouldFindAllEmployees_whenTheEmailMatches() {
        Employee tony = employeeFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        when(repository.findByEmailAddress(new EmailAddress("tony@avengers.com"))).thenReturn(Optional.of(tony));
        assertThat(primaryPort.findByEmailAddress("tony@avengers.com")).contains(tony);
    }

}
