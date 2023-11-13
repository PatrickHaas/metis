package io.metis.personal.adapters.persistence.berechtigung;

import io.metis.personal.domain.berechtigung.Berechtigung;
import io.metis.personal.domain.berechtigung.Berechtigungsbeschreibung;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class KeycloakBerechtigungMapper {

    public Berechtigung from(RoleRepresentation roleRepresentation) {
        Berechtigungsschluessel schluessel = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attributes -> attributes.get(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_PERMISSION_ID))
                .map(attributeValues -> attributeValues.get(0))
                .map(Berechtigungsschluessel::new)
                .orElseThrow();
        LocalDateTime initiatedAt = Optional.ofNullable(roleRepresentation.getAttributes())
                .map(attributes -> attributes.get(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_INITIATED_AT))
                .map(attributeValues -> attributeValues.get(0))
                .map(firstValue -> LocalDateTime.parse(firstValue, DateTimeFormatter.ISO_DATE_TIME))
                .orElseThrow();
        return new Berechtigung(schluessel, new Berechtigungsbeschreibung(roleRepresentation.getDescription()), initiatedAt);
    }

    public RoleRepresentation to(Berechtigung berechtigung) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(berechtigung.getId().value());
        roleRepresentation.setDescription(berechtigung.getBeschreibung().value());
        roleRepresentation.singleAttribute(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_PERMISSION_ID, berechtigung.getId().value());
        roleRepresentation.singleAttribute(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_INITIATED_AT, berechtigung.getInitiiertAm().format(DateTimeFormatter.ISO_DATE_TIME));
        return roleRepresentation;
    }
}
