package io.metis.personal.domain.mitarbeiter;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.domain.gruppe.GruppeId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@org.jmolecules.ddd.annotation.AggregateRoot
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mitarbeiter extends AggregateRoot<MitarbeiterId> {
    @org.jmolecules.ddd.annotation.Identity
    @EqualsAndHashCode.Include
    private final MitarbeiterId id;
    private Vorname vorname;
    private Nachname nachname;
    private Geburtsdatum geburtsdatum;
    private EinstelltAm einstelltAm;
    private EmailAdresse emailAdresse;
    private String jobTitel;
    private final Set<GruppeId> zugewieseneGruppen;

    Mitarbeiter(MitarbeiterId id, Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, EmailAdresse emailAdresse, String jobTitel) {
        this(id, vorname, nachname, geburtsdatum, null, emailAdresse, jobTitel, new HashSet<>());
    }

    public void einstellen() {
        einstelltAm = EinstelltAm.now();
        domainEvents().add(new MitarbeiterEingestellt(getId(), getEinstelltAm().value()));
    }

    public void aktualisiereDaten(Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, EmailAdresse emailAdresse, String jobTitle) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.emailAdresse = emailAdresse;
        this.jobTitel = jobTitle;
        domainEvents().add(new MitarbeiterdatenAktualisiert(getId()));
    }

    public void zuweisen(GruppeId gruppeId) {
        zugewieseneGruppen.add(gruppeId);
        domainEvents().add(new MitarbeiterEinerGruppeZugewiesen(getId(), gruppeId));
    }
}
