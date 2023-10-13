package io.metis.employees.application.mitarbeiter;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.employees.domain.mitarbeiter.EmailAdresse;
import io.metis.employees.domain.mitarbeiter.Mitarbeiter;
import io.metis.employees.domain.mitarbeiter.MitarbeiterFactory;
import io.metis.employees.domain.mitarbeiter.MitarbeiterRepository;
import io.metis.employees.domain.gruppe.GruppeId;
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
class MitarbeiterPrimaryPortTest {
    private final MitarbeiterFactory mitarbeiterFactory = new MitarbeiterFactory();
    @Mock
    private MitarbeiterRepository repository;
    @Mock
    private EventPublisher eventPublisher;

    private EmployeePrimaryPort primaryPort;

    @BeforeEach
    void createPrimaryPort() {
        primaryPort = new EmployeePrimaryPort(mitarbeiterFactory, repository, eventPublisher);
    }

    @Nested
    class HireMitarbeiterTests {
        @Test
        void hire_shouldHireAndSaveEmployee() {
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
            Mitarbeiter hiredMitarbeiter = primaryPort.hire(new HireEmployeeCommand("Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Engineer"));
            assertThat(hiredMitarbeiter.getId()).isNotNull();
            assertThat(hiredMitarbeiter.getVorname().value()).isEqualTo("Tony");
            assertThat(hiredMitarbeiter.getNachname().value()).isEqualTo("Stark");
            assertThat(hiredMitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(1980, 5, 28));
            assertThat(hiredMitarbeiter.getJobTitle()).isEqualTo("Engineer");

            for (DomainEvent domainEvent : hiredMitarbeiter.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }
    }

    @Nested
    class UpdateMitarbeiterTests {
        @Test
        void update_shouldUpdateAndSaveEmployee() {
            MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
            when(repository.findById(mitarbeiterId))
                    .thenReturn(Optional.of(mitarbeiterFactory.create(mitarbeiterId.value(), "Steve", "Rogers", LocalDate.of(1918, 7, 4), "steve@avengers.com", "Captain America")));
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
            Mitarbeiter updatedMitarbeiter = primaryPort.update(new UpdateEmployeeCommand(mitarbeiterId, "Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Iron-Man"));
            assertThat(updatedMitarbeiter.getId()).isNotNull();
            assertThat(updatedMitarbeiter.getVorname().value()).isEqualTo("Tony");
            assertThat(updatedMitarbeiter.getNachname().value()).isEqualTo("Stark");
            assertThat(updatedMitarbeiter.getGeburtsdatum().value()).isEqualTo(LocalDate.of(1980, 5, 28));
            assertThat(updatedMitarbeiter.getEmailAdresse().value()).isEqualTo("tony@avengers.com");
            assertThat(updatedMitarbeiter.getJobTitle()).isEqualTo("Iron-Man");

            for (DomainEvent domainEvent : updatedMitarbeiter.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }

        @Test
        void update_shouldRaiseEmployeeNotFoundException_whenEmployeeCouldNotBeFound() {
            MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
            when(repository.findById(mitarbeiterId))
                    .thenReturn(Optional.empty());
            assertThatThrownBy(() -> primaryPort.update(new UpdateEmployeeCommand(mitarbeiterId, "Tony", "Stark", LocalDate.of(1980, 5, 28), "tony@avengers.com", "Engineer")))
                    .isInstanceOf(EmployeeNotFoundException.class);
            verifyNoInteractions(eventPublisher);
        }
    }

    @Nested
    class AssignToGruppeTests {

        @Test
        void assignToGroup_shouldAssignSaveAndPublish() {
            MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
            when(repository.findById(mitarbeiterId)).thenReturn(Optional.of(mitarbeiterFactory.create(mitarbeiterId.value(), "Tony", "Stark", LocalDate.of(1970, 5, 28), "tony@avengers.com", "Iron-Man")));
            when(repository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

            GruppeId gruppeId = new GruppeId(UUID.randomUUID());
            Mitarbeiter updatedMitarbeiter = primaryPort.assignToGroup(new AssignToGroupCommand(gruppeId, mitarbeiterId));
            assertThat(updatedMitarbeiter.getAssignedGroups()).containsExactly(gruppeId);
            for (DomainEvent domainEvent : updatedMitarbeiter.domainEvents()) {
                verify(eventPublisher).publish(domainEvent);
            }
        }
    }

    @Test
    void existsById_shouldReturnTrue_whenEmployeeExists() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        when(repository.existsById(mitarbeiterId))
                .thenReturn(true);
        assertThat(primaryPort.existsById(mitarbeiterId)).isTrue();
    }

    @Test
    void existsById_shouldReturnFalse_whenEmployeeDoesNotExist() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        when(repository.existsById(mitarbeiterId))
                .thenReturn(false);
        assertThat(primaryPort.existsById(mitarbeiterId)).isFalse();
    }

    @Test
    void findAll_shouldReturnAllEmployees() {
        Mitarbeiter tony = mitarbeiterFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        Mitarbeiter bruce = mitarbeiterFactory.create(UUID.randomUUID(), "Bruce", "Banner", LocalDate.of(1970, 5, 29), "bruce@avengers.com", "Hulk");
        when(repository.findAll()).thenReturn(List.of(tony, bruce));
        assertThat(primaryPort.findAll()).containsExactlyInAnyOrder(tony, bruce);
    }

    @Test
    void deleteById_shouldDeleteById() {
        MitarbeiterId mitarbeiterId = new MitarbeiterId(UUID.randomUUID());
        doNothing().when(repository).deleteById(mitarbeiterId);
        primaryPort.deleteById(mitarbeiterId);
        verify(repository).deleteById(mitarbeiterId);
    }

    @Test
    void findByGroupId_shouldFindAllEmployees_assignedToASpecificGroup() {
        Mitarbeiter tony = mitarbeiterFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        Mitarbeiter bruce = mitarbeiterFactory.create(UUID.randomUUID(), "Bruce", "Banner", LocalDate.of(1970, 5, 29), "bruce@avengers.com", "Hulk");
        GruppeId gruppeId = new GruppeId(UUID.randomUUID());
        when(repository.findByGroupId(gruppeId)).thenReturn(List.of(tony, bruce));
        assertThat(primaryPort.findByGroupId(gruppeId)).containsExactlyInAnyOrder(tony, bruce);
    }

    @Test
    void findByEmailAddress_shouldFindAllEmployees_whenTheEmailMatches() {
        Mitarbeiter tony = mitarbeiterFactory.create(UUID.randomUUID(), "Tony", "Stark", LocalDate.of(1970, 5, 29), "tony@avengers.com", "Iron-Man");
        when(repository.findByEmailAddress(new EmailAdresse("tony@avengers.com"))).thenReturn(Optional.of(tony));
        assertThat(primaryPort.findByEmailAddress("tony@avengers.com")).contains(tony);
    }

}
