package io.metis.mitarbeiter.adapters.persistence.mitarbeiter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
interface MitarbeiterSpringRepository extends JpaRepository<MitarbeiterEntity, UUID> {
    Optional<MitarbeiterEntity> findByEmailAddress(String email);

    List<MitarbeiterEntity> findByAssignedGroupsContaining(UUID assignedGroup);
}
