package io.metis.common.adapters;

import io.metis.common.domain.DomainEvent;
import io.metis.common.domain.EventHandlerRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
class CommonEventHandlerRegistry implements EventHandlerRegistry {

    private final Map<Class<?>, List<Consumer<DomainEvent>>> subscribers = new HashMap<>();

    @EventListener(DomainEvent.class)
    void handleEvent(DomainEvent event) {
        for (Consumer<DomainEvent> eventHandler : subscribers.getOrDefault(event.getClass(), List.of())) {
            eventHandler.accept(event);
        }
    }

    @Override
    public <T extends DomainEvent> void subscribe(Class<T> domainEventClass, Consumer<T> eventHandler) {
        subscribers.computeIfAbsent(domainEventClass, k -> new ArrayList<>());
        subscribers.get(domainEventClass).add((Consumer<DomainEvent>) eventHandler);
    }

}
