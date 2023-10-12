package io.metis.common.domain;

import java.util.function.Consumer;

public interface EventHandlerRegistry {

    <T extends DomainEvent> void subscribe(Class<T> domainEventClass, Consumer<T> eventHandler);

}
