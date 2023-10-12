package io.metis.employees.domain.group;

import org.jmolecules.ddd.annotation.Factory;

import java.time.LocalDateTime;
import java.util.UUID;

@Factory
public class GroupFactory {


    public Group create(String name, String description) {
        return new Group(new GroupId(UUID.randomUUID()), new GroupName(name), new GroupDescription(description));
    }

    public Group create(UUID id, String name, String description, LocalDateTime initiatedAt) {
        return new Group(new GroupId(id), new GroupName(name), new GroupDescription(description), initiatedAt);
    }

}
