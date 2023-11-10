package io.metis.mitarbeiter.application;

import io.metis.mitarbeiter.application.berechtigung.BerechtigungPrimaryPort;
import io.metis.mitarbeiter.application.berechtigung.InitiiereBerechtigungCommand;
import io.metis.mitarbeiter.application.gruppe.GruppePrimaryPort;
import io.metis.mitarbeiter.application.gruppe.InitiiereGruppeCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterEinerGruppeZuweisenCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.application.mitarbeiter.StelleMitarbeiterEinCommand;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsbeschreibung;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.gruppe.Gruppenbeschreibung;
import io.metis.mitarbeiter.domain.gruppe.Gruppenname;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import io.metis.mitarbeiter.domain.mitarbeiter.MitarbeiterFactory;
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

import static io.metis.mitarbeiter.application.MitarbeiterModuleInitializer.ADMINISTRATION_GROUP_NAME;
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
                new InitiiereBerechtigungCommand("employees", "Genereller Zugriff zum Modul für Mitarbeiterstammdaten"),

                new InitiiereBerechtigungCommand("employees:employees:list", "Anzeige aller Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("employees:employees:show", "Einsicht in einzelne Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("employees:employees:hire", "Einstellen neuer Mitarbeiter"),
                new InitiiereBerechtigungCommand("employees:employees:edit", "Aktualisieren von Mitarbeiterstammdaten"),
                new InitiiereBerechtigungCommand("employees:employees:delete", "Entfernen von Mitarbeitern"),
                new InitiiereBerechtigungCommand("employees:employees:assigned-groups:show", "Gruppenzuweisungen von Mitarbeitern ansehen"),
                new InitiiereBerechtigungCommand("employees:employees:assign-to-group", "Mitarbeiter einer Gruppe zuweisen"),

                new InitiiereBerechtigungCommand("employees:groups:list", "Anzeige aller Gruppen"),
                new InitiiereBerechtigungCommand("employees:groups:initiate", "Neuer Gruppen initiieren")
        ).map(command -> {
            Berechtigung berechtigung = new Berechtigung(new Berechtigungsschluessel(command.key()), new Berechtigungsbeschreibung(command.description()));
            when(berechtigungPrimaryPort.initiiere(command))
                    .thenReturn(berechtigung);
            return berechtigung;
        }).toList();

        when(gruppePrimaryPort.findByName(ADMINISTRATION_GROUP_NAME)).thenReturn(Optional.empty());
        Gruppe administrationGruppe = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname(ADMINISTRATION_GROUP_NAME), new Gruppenbeschreibung("A group defining the administrators of a consultio application"), LocalDateTime.now());
        when(gruppePrimaryPort.initiiere(new InitiiereGruppeCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a consultio application")))
                .thenReturn(administrationGruppe);
        when(mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.de"))
                .thenReturn(Optional.of(mitarbeiterFactory.create(UUID.randomUUID(), "Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.de", "Application administrator")));

        initializer.initialize();

        for (Berechtigung berechtigung : berechtigungen) {
            verify(gruppePrimaryPort).weiseBerechtigungZu(administrationGruppe.getId(), berechtigung.getSchluessel());
        }
    }

    @Test
    void initialize_createsAdminEmployee_whenItDoesNotExist() {
        List<Berechtigung> berechtigungen = Stream.of(
                        new InitiiereBerechtigungCommand("employees", "Genereller Zugriff zum Modul für Mitarbeiterstammdaten"),

                        new InitiiereBerechtigungCommand("employees:employees:list", "Anzeige aller Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("employees:employees:show", "Einsicht in einzelne Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("employees:employees:hire", "Einstellen neuer Mitarbeiter"),
                        new InitiiereBerechtigungCommand("employees:employees:edit", "Aktualisieren von Mitarbeiterstammdaten"),
                        new InitiiereBerechtigungCommand("employees:employees:delete", "Entfernen von Mitarbeitern"),
                        new InitiiereBerechtigungCommand("employees:employees:assigned-groups:show", "Gruppenzuweisungen von Mitarbeitern ansehen"),
                        new InitiiereBerechtigungCommand("employees:employees:assign-to-group", "Mitarbeiter einer Gruppe zuweisen"),

                        new InitiiereBerechtigungCommand("employees:groups:list", "Anzeige aller Gruppen"),
                        new InitiiereBerechtigungCommand("employees:groups:initiate", "Neuer Gruppen initiieren")
                ).map(command -> new Berechtigung(new Berechtigungsschluessel(command.key()), new Berechtigungsbeschreibung(command.description())))
                .toList();

        when(berechtigungPrimaryPort.findAll()).thenReturn(berechtigungen);
        Gruppe administrationGruppe = new Gruppe(new GruppeId(UUID.randomUUID()), new Gruppenname(ADMINISTRATION_GROUP_NAME), new Gruppenbeschreibung("A group defining the administrators of a consultio application"), LocalDateTime.now());
        when(gruppePrimaryPort.findByName(ADMINISTRATION_GROUP_NAME)).thenReturn(Optional.of(administrationGruppe));
        when(mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.de"))
                .thenReturn(Optional.empty());
        Mitarbeiter mitarbeiter = mitarbeiterFactory.create(UUID.randomUUID(), "Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.de", "Application administrator");
        when(mitarbeiterPrimaryPort.stelleEin(new StelleMitarbeiterEinCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.de", "Application administrator")))
                .thenReturn(mitarbeiter);

        initializer.initialize();
        verify(mitarbeiterPrimaryPort).weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(administrationGruppe.getId(), mitarbeiter.getId()));
    }

}
