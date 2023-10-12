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
    private final GroupName name;
    private final GroupDescription description;
    private final Set<PermissionId> assignedPermissions;
    private LocalDateTime initiatedAt;

    public Group(GroupId id, GroupName name, GroupDescription description, LocalDateTime initiatedAt) {
        this(id, name, description, new HashSet<>(), initiatedAt);
    }

    public Group(GroupId id, GroupName name, GroupDescription description) {
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
