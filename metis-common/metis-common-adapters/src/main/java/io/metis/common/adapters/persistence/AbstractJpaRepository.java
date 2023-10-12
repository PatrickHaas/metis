package io.metis.common.adapters.persistence;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.Repository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class AbstractJpaRepository<R extends JpaRepository<E, EID>, T extends AggregateRoot<ID>, ID, E, EID> implements Repository<T, ID> {

    @Getter(AccessLevel.PROTECTED)
    private final R springRepository;
    @Getter(AccessLevel.PROTECTED)
    private final Function<T, E> toEntityMapper;
    @Getter(AccessLevel.PROTECTED)
    private final Function<E, T> fromEntityMapper;
    @Getter(AccessLevel.PROTECTED)
    private final Function<ID, EID> idMapper;

    @Override
    public T save(T entity) {
        return fromEntityMapper.apply(springRepository.save(toEntityMapper.apply(entity)));
    }

    @Override
    public Optional<T> findById(ID employeeId) {
        return springRepository.findById(idMapper.apply(employeeId))
                .map(fromEntityMapper::apply);
    }

    @Override
    public boolean existsById(ID id) {
        return springRepository.existsById(idMapper.apply(id));
    }

    @Override
    public List<T> findAll() {
        return springRepository.findAll().stream().map(fromEntityMapper::apply).toList();
    }

    @Override
    public void deleteAll() {
        springRepository.deleteAll();
    }

    @Override
    public void deleteById(ID id) {
        springRepository.deleteById(idMapper.apply(id));
    }
}
