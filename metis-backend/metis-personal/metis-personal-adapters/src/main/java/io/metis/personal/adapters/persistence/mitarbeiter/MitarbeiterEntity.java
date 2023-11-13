package io.metis.personal.adapters.persistence.mitarbeiter;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "employees", schema = "personal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MitarbeiterEntity {
    @Id
    private UUID id;
    private String vorname;
    private String nachname;
    private LocalDate geburtsdatum;
    private LocalDate eingestelltAm;
    private String emailadresse;
    private String jobBeschreibung;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(schema = "personal", name = "mitarbeiter_zugewiesene_gruppen", joinColumns = @JoinColumn(name = "mitarbeiter_id"))
    private Set<UUID> zugewieseneGruppen;
}
