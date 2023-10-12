package io.metis.common.adapters.persistence;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AbstractInMemoryRepository<T extends AggregateRoot<ID>, ID> implements Repository<T, ID> {

    private final Map<ID, T> cache = new HashMap<>();

    @Override
    public T save(T entity) {
        cache.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public boolean existsById(ID id) {
        return cache.containsKey(id);
    }

    @Override
    public List<T> findAll() {
        return cache.values().stream().toList();
    }

    @Override
    public void deleteAll() {
        cache.clear();
    }

    @Override
    public void deleteById(ID id) {
        cache.remove(id);
    }
}
