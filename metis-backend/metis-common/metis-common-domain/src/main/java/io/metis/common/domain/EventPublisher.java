package io.metis.common.domain;

public interface EventPublisher {
    void publish(DomainEvent events);
}
