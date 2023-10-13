package io.metis.employees.application.group;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.group.*;
import io.metis.employees.domain.permission.PermissionId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupPrimaryPortTest {
    @Mock
    private GroupRepository repository;
    @Mock
    private EventPublisher eventPublisher;
    @Spy
    private GroupFactory factory;

    @InjectMocks
    private GroupPrimaryPort primaryPort;

    @Test
    void initiate_shouldSaveAndPublish() {
        when(repository.findByName("CH-1")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Group group = primaryPort.initiate(new InitiateGroupCommand("CH-1", "Chapter 1 is the best"));
        assertThat(group.getId()).isNotNull();
        assertThat(group.getName().value()).isEqualTo("CH-1");
        assertThat(group.getDescription().value()).isEqualTo("Chapter 1 is the best");
        assertThat(group.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());

        verify(factory).create("CH-1", "Chapter 1 is the best");

        for (DomainEvent domainEvent : group.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
    }

    @Test
    void initiate_shouldRaiseException_whenGroupNameIsAlreadyTaken() {
        when(repository.findByName("CH-1")).thenReturn(Optional.of(new Group(new GroupId(UUID.randomUUID()), new GroupName("CH-1"), new GroupDescription("Chapter 1 is the best"),
                LocalDateTime.now())));
        assertThatThrownBy(() -> primaryPort.initiate(new InitiateGroupCommand("CH-1", "Chapter 1")))
                .isInstanceOf(GroupNameAlreadyTakenException.class);

        verifyNoInteractions(eventPublisher);
    }

    @Test
    void findByName() {
        Group group = new Group(new GroupId(UUID.randomUUID()), new GroupName("Chapter 1"), null, LocalDateTime.now());
        when(repository.findByName("Chapter 1")).thenReturn(Optional.of(group));
        assertThat(primaryPort.findByName("Chapter 1")).contains(group);
    }

    @Test
    void findById() {
        GroupId groupId = new GroupId(UUID.randomUUID());
        Group group = new Group(groupId, new GroupName("Chapter 1"), null, LocalDateTime.now());
        when(repository.findById(groupId)).thenReturn(Optional.of(group));
        assertThat(primaryPort.findById(groupId)).isEqualTo(group);
    }

    @Test
    void findAll() {
        Group chapter1 = new Group(new GroupId(UUID.randomUUID()), new GroupName("Chapter 1"), null, LocalDateTime.now());
        Group chapter2 = new Group(new GroupId(UUID.randomUUID()), new GroupName("Chapter 2"), null, LocalDateTime.now());
        when(repository.findAll()).thenReturn(List.of(chapter2, chapter1));
        assertThat(primaryPort.findAll()).containsExactlyInAnyOrder(chapter1, chapter2);
    }

    @Test
    void assignPermission_shouldAssignPermissionToGroupSaveAndPublish() {
        GroupId groupId = new GroupId(UUID.randomUUID());
        Group group = new Group(groupId, new GroupName("Chapter 1"), null, LocalDateTime.now());
        when(repository.findById(groupId)).thenReturn(Optional.of(group));

        PermissionId permissionId = new PermissionId(UUID.randomUUID());
        primaryPort.assignPermission(groupId, permissionId);

        assertThat(group.getAssignedPermissions()).containsExactly(permissionId);

        verify(repository).save(group);
        for (DomainEvent domainEvent : group.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
    }

}
