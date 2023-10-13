package io.metis.mitarbeiter.adapters.persistence.employee;


import io.metis.common.adapters.persistence.AbstractJpaRepository;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.mitarbeiter.EmailAdresse;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterRepository;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class JpaMitarbeiterRepository extends AbstractJpaRepository<SpringEmployeeRepository, Mitarbeiter, MitarbeiterId, EmployeeEntity, UUID> implements MitarbeiterRepository {

    public JpaMitarbeiterRepository(SpringEmployeeRepository repository, JpaEmployeeMapper mapper) {
        super(repository, mapper::to, mapper::from, MitarbeiterId::value);
    }

    @Override
    public Optional<Mitarbeiter> findByEmailAddress(EmailAdresse email) {
        return getSpringRepository().findByEmailAddress(email.value())
                .map(getFromEntityMapper());
    }

    @Override
    public List<Mitarbeiter> findByGroupId(GruppeId gruppeId) {
        return getSpringRepository().findByAssignedGroupsContaining(gruppeId.value()).stream()
                .map(getFromEntityMapper())
                .toList();
    }
}
