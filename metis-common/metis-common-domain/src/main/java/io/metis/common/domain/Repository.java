package io.metis.common.domain;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends AggregateRoot, ID> {

    T save(T entity);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    List<T> findAll();

    void deleteAll();

    void deleteById(ID id);
}
