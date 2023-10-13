package io.metis.mitarbeiter.application;

import io.metis.common.application.ModuleInitializer;
import io.metis.mitarbeiter.application.berechtigung.BerechtigungPrimaryPort;
import io.metis.mitarbeiter.application.berechtigung.InitiiereBerechtigungCommand;
import io.metis.mitarbeiter.application.gruppe.GruppePrimaryPort;
import io.metis.mitarbeiter.application.gruppe.InitiiereGruppeCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterEinerGruppeZuweisenCommand;
import io.metis.mitarbeiter.application.mitarbeiter.MitarbeiterPrimaryPort;
import io.metis.mitarbeiter.application.mitarbeiter.StelleMitarbeiterEinCommand;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.mitarbeiter.Mitarbeiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class MitarbeiterModuleInitializer implements ModuleInitializer {

    public static final String ADMINISTRATION_GROUP_NAME = "administration";

    private final BerechtigungPrimaryPort berechtigungPrimaryPort;
    private final GruppePrimaryPort gruppePrimaryPort;
    private final MitarbeiterPrimaryPort mitarbeiterPrimaryPort;

    @Override
    public void initialize() {
        List<Berechtigung> newBerechtigungs = getOrCreatePermissions();
        Gruppe administrationGruppe = getOrCreateAdministrationGroup(newBerechtigungs);
        if (mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.de").isEmpty()) {
            Mitarbeiter administrator = mitarbeiterPrimaryPort.stelleEin(new StelleMitarbeiterEinCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.de", "Application administrator"));
            log.debug("created application administrator, id = {}", administrator.getId().value());
            mitarbeiterPrimaryPort.weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(administrationGruppe.getId(), administrator.getId()));
            log.debug("assigned application administrator to administration group {}", administrationGruppe.getName());
        }
    }

    private List<Berechtigung> getOrCreatePermissions() {
        List<Berechtigung> existierendeBerechtigungen = berechtigungPrimaryPort.findAll();
        List<InitiiereBerechtigungCommand> initiiereBerechtigungCommands = List.of(
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
        );

        List<String> existingPermissionKeys = existierendeBerechtigungen.stream()
                .map(Berechtigung::getSchluessel)
                .map(Berechtigungsschluessel::value).toList();
        List<InitiiereBerechtigungCommand> newInitiiereBerechtigungCommands = initiiereBerechtigungCommands.stream().filter(command -> !existingPermissionKeys.contains(command.key())).toList();
        List<Berechtigung> newBerechtigungs = new ArrayList<>();
        for (InitiiereBerechtigungCommand newPermission : newInitiiereBerechtigungCommands) {
            newBerechtigungs.add(berechtigungPrimaryPort.initiiere(newPermission));
            log.debug("initiated permission, key = {}, description = {}", newPermission.key(), newPermission.description());
        }
        return newBerechtigungs;
    }

    private Gruppe getOrCreateAdministrationGroup(List<Berechtigung> neueBerechtigungen) {
        Gruppe administrationGruppe = gruppePrimaryPort.findByName(ADMINISTRATION_GROUP_NAME).orElse(null);
        if (administrationGruppe == null) {
            administrationGruppe = gruppePrimaryPort.initiiere(new InitiiereGruppeCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a consultio application"));
            log.debug("initiated group, name = {}, description = {}", administrationGruppe.getName(), administrationGruppe.getBeschreibung());
        }
        for (Berechtigung newBerechtigung : neueBerechtigungen) {
            gruppePrimaryPort.weiseBerechtigungZu(administrationGruppe.getId(), newBerechtigung.getId());
            log.debug("assigned permission {} to group {}", newBerechtigung.getSchluessel(), administrationGruppe.getName());
        }
        return administrationGruppe;
    }
}
