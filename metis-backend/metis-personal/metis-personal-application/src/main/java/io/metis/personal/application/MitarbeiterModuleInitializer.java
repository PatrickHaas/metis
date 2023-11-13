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
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import io.metis.personal.domain.gruppe.Gruppe;
import io.metis.personal.domain.mitarbeiter.Mitarbeiter;
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
    public void initialize(Configuration configuration) {
        List<Berechtigung> neueBerechtigungen = getOrCreatePermissions();
        Gruppe administrationGruppe = getOrCreateAdministrationGroup(neueBerechtigungen);
        if (mitarbeiterPrimaryPort.findByEmailAddress("administrator@metis.io").isEmpty()) {
            Mitarbeiter administrator = mitarbeiterPrimaryPort.stelleEin(new StelleMitarbeiterEinCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@metis.io", "Application administrator"));
            log.debug("created application administrator, id = {}", administrator.getId().value());
            mitarbeiterPrimaryPort.weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(administrationGruppe.getId(), administrator.getId()));
            log.debug("assigned application administrator to administration group {}", administrationGruppe.getName());
        }
    }

    private List<Berechtigung> getOrCreatePermissions() {
        List<Berechtigung> existierendeBerechtigungen = berechtigungPrimaryPort.findAll();
        List<InitiiereBerechtigungCommand> initiiereBerechtigungCommands = List.of(
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
        );

        List<String> existingPermissionKeys = existierendeBerechtigungen.stream()
                .map(Berechtigung::getSchluessel)
                .map(Berechtigungsschluessel::value).toList();
        List<InitiiereBerechtigungCommand> newInitiiereBerechtigungCommands = initiiereBerechtigungCommands.stream().filter(command -> !existingPermissionKeys.contains(command.key())).toList();
        List<Berechtigung> neueBerechtigungen = new ArrayList<>();
        for (InitiiereBerechtigungCommand newPermission : newInitiiereBerechtigungCommands) {
            neueBerechtigungen.add(berechtigungPrimaryPort.initiiere(newPermission));
            log.debug("initiated permission, key = {}, description = {}", newPermission.key(), newPermission.description());
        }
        return neueBerechtigungen;
    }

    private Gruppe getOrCreateAdministrationGroup(List<Berechtigung> neueBerechtigungen) {
        Gruppe administrationGruppe = gruppePrimaryPort.findByName(ADMINISTRATION_GROUP_NAME).orElse(null);
        if (administrationGruppe == null) {
            administrationGruppe = gruppePrimaryPort.initiiere(new InitiiereGruppeCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a metis application"));
            log.debug("initiated group, name = {}, description = {}", administrationGruppe.getName(), administrationGruppe.getBeschreibung());
        }
        for (Berechtigung newBerechtigung : neueBerechtigungen) {
            gruppePrimaryPort.weiseBerechtigungZu(administrationGruppe.getId(), newBerechtigung.getId());
            log.debug("assigned permission {} to group {}", newBerechtigung.getSchluessel(), administrationGruppe.getName());
        }
        return administrationGruppe;
    }
}
