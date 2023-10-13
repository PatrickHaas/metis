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
        if (mitarbeiterPrimaryPort.findByEmailAddress("administrator@consultio.de").isEmpty()) {
            Mitarbeiter administrator = mitarbeiterPrimaryPort.stelleEin(new StelleMitarbeiterEinCommand("Arnold", "Admin", LocalDate.of(1970, 1, 1), "administrator@consultio.de", "Application administrator"));
            log.debug("created application administrator, id = {}", administrator.getId().value());
            mitarbeiterPrimaryPort.weiseGruppeZu(new MitarbeiterEinerGruppeZuweisenCommand(administrationGruppe.getId(), administrator.getId()));
            log.debug("assigned application administrator to administration group {}", administrationGruppe.getName());
        }
    }

    private List<Berechtigung> getOrCreatePermissions() {
        List<Berechtigung> existingBerechtigungs = berechtigungPrimaryPort.findAll();
        List<InitiiereBerechtigungCommand> initiiereBerechtigungCommands = List.of(
                new InitiiereBerechtigungCommand("employees", ""),

                new InitiiereBerechtigungCommand("employees:employees:list", ""),
                new InitiiereBerechtigungCommand("employees:employees:show", ""),
                new InitiiereBerechtigungCommand("employees:employees:hire", ""),
                new InitiiereBerechtigungCommand("employees:employees:edit", ""),
                new InitiiereBerechtigungCommand("employees:employees:delete", ""),
                new InitiiereBerechtigungCommand("employees:employees:assigned-groups:show", ""),
                new InitiiereBerechtigungCommand("employees:employees:assign-to-group", ""),

                new InitiiereBerechtigungCommand("employees:groups:list", ""),
                new InitiiereBerechtigungCommand("employees:groups:initiate", "")
        );

        List<String> existingPermissionKeys = existingBerechtigungs.stream()
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

    private Gruppe getOrCreateAdministrationGroup(List<Berechtigung> newBerechtigungs) {
        Gruppe administrationGruppe = gruppePrimaryPort.findByName(ADMINISTRATION_GROUP_NAME).orElse(null);
        if (administrationGruppe == null) {
            administrationGruppe = gruppePrimaryPort.initiiere(new InitiiereGruppeCommand(ADMINISTRATION_GROUP_NAME, "A group defining the administrators of a consultio application"));
            log.debug("initiated group, name = {}, description = {}", administrationGruppe.getName(), administrationGruppe.getBeschreibung());
        }
        for (Berechtigung newBerechtigung : newBerechtigungs) {
            gruppePrimaryPort.weiseBerechtigungZu(administrationGruppe.getId(), newBerechtigung.getId());
            log.debug("assigned permission {} to group {}", newBerechtigung.getSchluessel(), administrationGruppe.getName());
        }
        return administrationGruppe;
    }
}
