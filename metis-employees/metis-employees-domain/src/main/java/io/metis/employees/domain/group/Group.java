package io.metis.employees.domain.group;

import io.metis.common.domain.AggregateRoot;
import io.metis.employees.domain.permission.PermissionId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Group extends AggregateRoot<GroupId> {
    @Identity
    private final GroupId id;
    private final String name;
    private final String description;
    private final Set<PermissionId> assignedPermissions;
    private LocalDateTime initiatedAt;

    public Group(GroupId id, String name, String description, LocalDateTime initiatedAt) {
        this(id, name, description, new HashSet<>(), initiatedAt);
    }

    public Group(GroupId id, String name, String description) {
        this(id, name, description, new HashSet<>(), null);
    }

    public void initiate() {
        initiatedAt = LocalDateTime.now();
        domainEvents().add(new GroupInitiated(getId()));
    }

    public void assignPermission(PermissionId permissionId) {
        assignedPermissions.add(permissionId);
        domainEvents().add(new PermissionAssigned(getId(), permissionId));
    }
}
