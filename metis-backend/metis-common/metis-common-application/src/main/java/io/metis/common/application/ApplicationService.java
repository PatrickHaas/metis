package io.metis.common.application;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.EventPublisher;
import io.metis.common.domain.Repository;

public interface ApplicationService {

    default <E extends AggregateRoot<ID>, ID> E saveAndPublish(E entity, Repository<E, ID> repository, EventPublisher eventPublisher) {
        E saved = repository.save(entity);
        entity.domainEvents().forEach(eventPublisher::publish);
        entity.domainEvents().clear();
        return saved;
    }

}
