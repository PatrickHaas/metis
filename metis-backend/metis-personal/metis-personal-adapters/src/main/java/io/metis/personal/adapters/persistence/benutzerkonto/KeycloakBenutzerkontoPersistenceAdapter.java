package io.metis.personal.adapters.persistence.benutzerkonto;

import io.metis.common.domain.mitarbeiter.MitarbeiterId;
import io.metis.personal.adapters.persistence.KeycloakAttributeAccessor;
import io.metis.personal.adapters.persistence.KeycloakConfiguration;
import io.metis.personal.domain.benutzerkonto.Benutzerkonto;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoId;
import io.metis.personal.domain.benutzerkonto.BenutzerkontoRepository;
import io.metis.personal.domain.gruppe.GruppeId;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;

import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeycloakBenutzerkontoPersistenceAdapter implements BenutzerkontoRepository {
    static final String ATTRIBUTE_EMPLOYEE_ID = "employee_id";

    private final Keycloak keycloak;
    private final KeycloakAttributeAccessor attributeAccessor;
    private final KeycloakConfiguration configuration;

    @Override
    public Benutzerkonto save(Benutzerkonto benutzerkonto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(benutzerkonto.getVorname().toLowerCase() + "." + benutzerkonto.getNachname().toLowerCase());
        userRepresentation.setEmail(benutzerkonto.getEmailAdresse());
        userRepresentation.setFirstName(benutzerkonto.getVorname());
        userRepresentation.setLastName(benutzerkonto.getNachname());
        userRepresentation.singleAttribute(ATTRIBUTE_EMPLOYEE_ID, benutzerkonto.getMitarbeiterId().value().toString());

        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        UsersResource usersResource = realmResource.users();

        return usersResource.search(userRepresentation.getUsername()).stream().findFirst()
                .map(existingUserRepresentation -> {
                    usersResource.get(existingUserRepresentation.getId()).update(userRepresentation);
                    return existingUserRepresentation;
                })
                .map(user -> new Benutzerkonto(
                        new BenutzerkontoId(UUID.fromString(user.getId())),
                        benutzerkonto.getMitarbeiterId(),
                        benutzerkonto.getVorname(),
                        benutzerkonto.getNachname(),
                        benutzerkonto.getEmailAdresse())
                ).orElseGet(() -> {
                    try (Response response = usersResource.create(userRepresentation)) {
                        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
                            String userIdAsString = CreatedResponseUtil.getCreatedId(response);
                            UserResource userResource = usersResource.get(userIdAsString);
                            if (!configuration.getUserCreationActions().isEmpty()) {
                                userResource.executeActionsEmail(configuration.getUserCreationActions());
                            }
                            return new Benutzerkonto(
                                    new BenutzerkontoId(UUID.fromString(userIdAsString)),
                                    benutzerkonto.getMitarbeiterId(),
                                    benutzerkonto.getVorname(),
                                    benutzerkonto.getNachname(),
                                    benutzerkonto.getEmailAdresse());
                        } else {
                            throw new PersistenceException("failed to create user , " + response.getStatusInfo());
                        }
                    }
                });
    }

    @Override
    public void assignGroupByEmployeeId(MitarbeiterId mitarbeiterId, GruppeId gruppeId) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupRepresentation groupRepresentation = realmResource.groups().groups().stream()
                .map(group -> realmResource.groups().group(group.getId()).toRepresentation())
                .filter(role -> Objects.equals(gruppeId.value().toString(), attributeAccessor.getAttributeValue(role.getAttributes(), "group_id")))
                .findFirst().orElseThrow();
        UserRepresentation userRepresentation = realmResource.users().searchByAttributes(ATTRIBUTE_EMPLOYEE_ID + ":" + mitarbeiterId.value().toString()).stream().findFirst().orElseThrow();
        realmResource.users().get(userRepresentation.getId()).joinGroup(groupRepresentation.getId());
    }
}
