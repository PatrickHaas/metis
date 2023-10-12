package io.metis.common.domain;

import java.util.LinkedList;
import java.util.Queue;

public abstract class AggregateRoot<ID> {
    private final Queue<DomainEvent> domainEvents = new LinkedList<>();

    public final Queue<DomainEvent> domainEvents() {
        return domainEvents;
    }

    public abstract ID getId();

}
