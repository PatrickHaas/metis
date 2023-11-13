package io.metis.personal.adapters.persistence.mitarbeiter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface MitarbeiterSpringRepository extends JpaRepository<MitarbeiterEntity, UUID> {
    Optional<MitarbeiterEntity> findByEmailadresse(String email);

    List<MitarbeiterEntity> findByZugewieseneGruppenContaining(UUID assignedGroup);
}
