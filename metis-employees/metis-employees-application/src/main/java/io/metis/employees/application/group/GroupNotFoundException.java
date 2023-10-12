package io.metis.employees.application.group;


import io.metis.employees.domain.group.GroupId;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(GroupId id) {
        super("A group with the id %s could not be found".formatted(id.value()));
    }

    public GroupNotFoundException(String name) {
        super("A group with the name %s could not be found".formatted(name));
    }
}
