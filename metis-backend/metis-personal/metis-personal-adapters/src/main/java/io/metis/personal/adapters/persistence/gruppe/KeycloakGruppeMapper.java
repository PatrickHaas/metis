package io.metis.personal.adapters.persistence.gruppe;

import io.metis.personal.domain.berechtigung.Berechtigung;
import io.metis.personal.domain.berechtigung.BerechtigungRepository;
import io.metis.personal.domain.berechtigung.Berechtigungsschluessel;
import io.metis.personal.domain.gruppe.Gruppe;
import io.metis.personal.domain.gruppe.GruppeId;
import io.metis.personal.domain.gruppe.Gruppenbeschreibung;
import io.metis.personal.domain.gruppe.Gruppenname;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.GroupRepresentation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class KeycloakGruppeMapper {

    private final BerechtigungRepository berechtigungRepository;

    public Gruppe from(GroupRepresentation groupRepresentation) {
        GruppeId gruppeId = Optional.ofNullable(groupRepresentation.getAttributes())
                .map(attributes -> attributes.get(KeycloakGruppePersistenceAdapter.ATTRIBUTE_GROUP_ID))
                .map(attributeValues -> attributeValues.get(0))
                .map(UUID::fromString)
                .map(GruppeId::new)
                .orElseThrow();
        String description = Optional.ofNullable(groupRepresentation.getAttributes())
                .map(attributes -> attributes.get(KeycloakGruppePersistenceAdapter.ATTRIBUTE_DESCRIPTION))
                .map(attributeValues -> attributeValues.get(0))
                .orElseThrow();
        LocalDateTime initiatedAt = Optional.ofNullable(groupRepresentation.getAttributes())
                .map(attributes -> attributes.get(KeycloakGruppePersistenceAdapter.ATTRIBUTE_INITIATED_AT))
                .map(attributeValues -> attributeValues.get(0))
                .map(firstValue -> LocalDateTime.parse(firstValue, DateTimeFormatter.ISO_DATE_TIME))
                .orElseThrow();
        Set<Berechtigungsschluessel> assignedPermissions = groupRepresentation.getRealmRoles().stream()
                .map(Berechtigungsschluessel::new)
                .map(berechtigungRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .map(Berechtigung::getId)
                .collect(Collectors.toSet());
        return new Gruppe(gruppeId, new Gruppenname(groupRepresentation.getName()), new Gruppenbeschreibung(description), assignedPermissions, initiatedAt);
    }

    public GroupRepresentation to(Gruppe gruppe) {
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(gruppe.getName().value());
        groupRepresentation.singleAttribute(KeycloakGruppePersistenceAdapter.ATTRIBUTE_DESCRIPTION, gruppe.getBeschreibung().value());
        groupRepresentation.singleAttribute(KeycloakGruppePersistenceAdapter.ATTRIBUTE_GROUP_ID, gruppe.getId().value().toString());
        groupRepresentation.singleAttribute(KeycloakGruppePersistenceAdapter.ATTRIBUTE_INITIATED_AT, gruppe.getInitiiertAm().format(DateTimeFormatter.ISO_DATE_TIME));
        return groupRepresentation;
    }
}
