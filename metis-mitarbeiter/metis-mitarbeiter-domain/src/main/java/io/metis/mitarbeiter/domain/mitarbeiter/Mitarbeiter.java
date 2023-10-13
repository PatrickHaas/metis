package io.metis.mitarbeiter.domain.mitarbeiter;

import io.metis.common.domain.AggregateRoot;
import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
@org.jmolecules.ddd.annotation.AggregateRoot
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Mitarbeiter extends AggregateRoot<MitarbeiterId> {
    @org.jmolecules.ddd.annotation.Identity
    private final MitarbeiterId id;
    private Vorname vorname;
    private Nachname nachname;
    private Geburtsdatum geburtsdatum;
    private EinstelltAm einstelltAm;
    private EmailAdresse emailAdresse;
    private String jobTitle;
    private final Set<GruppeId> assignedGroups;

    Mitarbeiter(MitarbeiterId id, Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, EmailAdresse emailAdresse, String jobTitle) {
        this(id, vorname, nachname, geburtsdatum, null, emailAdresse, jobTitle, new HashSet<>());
    }

    public void einstellen() {
        einstelltAm = EinstelltAm.now();
        domainEvents().add(new MitarbeiterEingestellt(getId(), getEinstelltAm().value()));
    }

    public void update(Vorname vorname, Nachname nachname, Geburtsdatum geburtsdatum, EmailAdresse emailAdresse, String jobTitle) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.geburtsdatum = geburtsdatum;
        this.emailAdresse = emailAdresse;
        this.jobTitle = jobTitle;
        domainEvents().add(new MitarbeiterdatenAktualisiert(getId()));
    }

    public void zuweisen(GruppeId gruppeId) {
        assignedGroups.add(gruppeId);
        domainEvents().add(new MitarbeiterEinerGruppeZugewiesen(getId(), gruppeId));
    }
}
