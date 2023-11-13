package io.metis.personal.application;

import io.metis.common.application.ModuleInitializer;
import io.metis.personal.application.berechtigung.BerechtigungPrimaryPort;
import io.metis.personal.application.berechtigung.InitiiereBerechtigungCommand;
import io.metis.personal.application.gruppe.GruppePrimaryPort;
import io.metis.personal.application.gruppe.InitiiereGruppeCommand;
import io.metis.personal.application.mitarbeiter.MitarbeiterEinerGruppeZuweisenCommand;
import io.metis.personal.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.personal.application.mitarbeiter.StelleMitarbeiterEinCommand;
import io.metis.personal.domain.berechtigung.Berechtigung;
import io.metis.personal.domain.berechtigung.Berechtigungsbeschreibung;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import io.metis.personal.domain.gruppe.Gruppe;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.gruppe.Gruppenbeschreibung;
import io.metis.personal.domain.gruppe.Gruppenname;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
import io.metis.personal.domain.mitarbeiter.MitarbeiterFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MitarbeiterModuleInitializerTest {

    @Mock
    private BerechtigungPrimaryPort berechtigungPrimaryPort;
    @Mock
    private GruppePrimaryPort gruppePrimaryPort;
    @Mock
    private MitarbeiterPrimaryPort mitarbeiterPrimaryPort;
    private final MitarbeiterFactory mitarbeiterFactory = new MitarbeiterFactory();
    @InjectMocks
    private MitarbeiterModuleInitializer initializer;

    @Test
    void initialize_createsAdminGroup_whenItDoesNotExist() {
        when(berechtigungPrimaryPort.findAll()).thenReturn(Collections.emptyList());
        List<Berechtigung> berechtigungen = Stream.of(
                new InitiiereBerechtigungCommand("personnel", "Genereller Zugriff zum Modul für Mitarbeiterstammdaten"),

                new InitiiereBerechtigungCommand("personnel:employees:list", "Anzeige aller Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("personnel:employees:show", "Einsicht in einzelne Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("personnel:employees:hire", "Einstellen neuer Mitarbeiter"),
                new InitiiereBerechtigungCommand("personnel:employees:edit", "Aktualisieren von Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("personnel:employees:delete", "Entfernen von Mitarbeitern"),
                new InitiiereBerechtigungCommand("personnel:employees:assigned-groups:show", "Gruppenzuweisungen von Mitarbeitern ansehen"),
                new InitiiereBerechtigungCommand("personnel:employees:assign-to-group", "Mitarbeiter einer Gruppe zuweisen"),

                new InitiiereBerechtigungCommand("personnel:groups:list", "Anzeige aller Gruppen"),
                new InitiiereBerechtigungCommand("personnel:groups:initiate", "Neuer Gruppen initiieren")
        ).map(command -> {
            Berechtigung berechtigung = new Berechtigung(new Berechtigungsschluessel(command.key()), new Berechtigungsbeschreibung(command.description()));
            when(berechtigungPrimaryPort.initiiere(command))
                    .thenReturn(berechtigung);
            return berechtigung;
        }).toList();

        when(gruppePrimaryPort.findByName(MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME)).thenReturn(Optional.empty());
        Gruppe administrationGruppe = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname(MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME), new Gruppenbeschreibung("A group defining the administrators of a metis application"), LocalDateTime.now());
        when(gruppePrimaryPort.initiiere(new InitiiereGruppeCommand(MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a metis application")))
                .thenReturn(administrationGruppe);
        when(mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.io"))
                .thenReturn(Optional.of(mitarbeiterFactory.create(UUID.randomUUID(), "Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.io", "Application administrator")));

        initializer.initialize(new ModuleInitializer.Configuration(false));

        for (Berechtigung berechtigung : berechtigungen) {
            verify(gruppePrimaryPort).weiseBerechtigungZu(administrationGruppe.getId(), berechtigung.getSchluessel());
        }
    }

    @Test
    void initialize_createsAdminEmployee_whenItDoesNotExist() {
        List<Berechtigung> berechtigungen = Stream.of(
                        new InitiiereBerechtigungCommand("personnel", "Genereller Zugriff zum Modul für Mitarbeiterstammdaten"),

                        new InitiiereBerechtigungCommand("personnel:employees:list", "Anzeige aller Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("personnel:employees:show", "Einsicht in einzelne Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("personnel:employees:hire", "Einstellen neuer Mitarbeiter"),
                        new InitiiereBerechtigungCommand("personnel:employees:edit", "Aktualisieren von Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("personnel:employees:delete", "Entfernen von Mitarbeitern"),
                        new InitiiereBerechtigungCommand("personnel:employees:assigned-groups:show", "Gruppenzuweisungen von Mitarbeitern ansehen"),
                        new InitiiereBerechtigungCommand("personnel:employees:assign-to-group", "Mitarbeiter einer Gruppe zuweisen"),

                        new InitiiereBerechtigungCommand("personnel:groups:list", "Anzeige aller Gruppen"),
                        new InitiiereBerechtigungCommand("personnel:groups:initiate", "Neuer Gruppen initiieren")
                ).map(command -> new Berechtigung(new Berechtigungsschluessel(command.key()), new Berechtigungsbeschreibung(command.description())))
                .toList();

        when(berechtigungPrimaryPort.findAll()).thenReturn(berechtigungen);
        Gruppe administrationGruppe = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname(MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME), new Gruppenbeschreibung("A group defining the administrators of a metis application"), LocalDateTime.now());
        when(gruppePrimaryPort.findByName(MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME)).thenReturn(Optional.of(administrationGruppe));
        when(mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.io"))
                .thenReturn(Optional.empty());
        Mitarbeiter mitarbeiter = mitarbeiterFactory.create(UUID.randomUUID(), "Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.io", "Application administrator");
        when(mitarbeiterPrimaryPort.stelleEin(new StelleMitarbeiterEinCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.io", "Application administrator")))
                .thenReturn(mitarbeiter);

        initializer.initialize(new ModuleInitializer.Configuration(false));
        verify(mitarbeiterPrimaryPort).weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(administrationGruppe.getId(), mitarbeiter.getId()));
    }

}
