package io.metis.employees.application.group;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventPublisher;
import io.metis.employees.domain.group.Group;
import io.metis.employees.domain.group.GroupId;
import io.metis.employees.domain.group.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @InjectMocks
    private GroupPrimaryPort primaryPort;

    @Test
    void initiate_shouldSaveAndPublish() {
        when(repository.findByName("CH-1")).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Group group = primaryPort.initiate(new InitiateGroupCommand("CH-1", "Chapter 1"));
        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo("CH-1");
        assertThat(group.getDescription()).isEqualTo("Chapter 1");
        assertThat(group.getInitiatedAt().toLocalDate()).isEqualTo(LocalDate.now());

        for (DomainEvent domainEvent : group.domainEvents()) {
            verify(eventPublisher).publish(domainEvent);
        }
    }

    @Test
    void initiate_shouldRaiseException_whenGroupNameIsAlreadyTaken() {
        when(repository.findByName("CH-1")).thenReturn(Optional.of(new Group(new GroupId(UUID.randomUUID()), "CH-1", "Chapter 1",
                LocalDateTime.now())));
        assertThatThrownBy(() -> primaryPort.initiate(new InitiateGroupCommand("CH-1", "Chapter 1")))
                .isInstanceOf(GroupNameAlreadyTakenException.class);

        verifyNoInteractions(eventPublisher);
    }


}
