package io.metis.employees.domain.permission;

import io.metis.common.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@org.jmolecules.ddd.annotation.AggregateRoot
public class Permission extends AggregateRoot<PermissionId> {
    @Identity
    private final PermissionId id;
    private final String key;
    private final String description;
    private LocalDateTime initiatedAt;

    public Permission(PermissionId id, String key, String description) {
        this.id = id;
        this.key = key;
        this.description = description;
    }

    public void initiate() {
        initiatedAt = LocalDateTime.now();
        domainEvents().add(new PermissionInitiated(getId()));
    }
}
