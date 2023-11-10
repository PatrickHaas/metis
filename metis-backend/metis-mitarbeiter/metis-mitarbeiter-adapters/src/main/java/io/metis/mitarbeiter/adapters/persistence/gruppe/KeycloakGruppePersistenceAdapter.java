package io.metis.mitarbeiter.adapters.persistence.gruppe;

import io.metis.mitarbeiter.adapters.persistence.KeycloakAttributeAccessor;
import io.metis.mitarbeiter.adapters.persistence.KeycloakConfiguration;
import io.metis.mitarbeiter.adapters.persistence.berechtigung.KeycloakBerechtigungPersistenceAdapter;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import io.metis.mitarbeiter.domain.gruppe.Gruppe;
import io.metis.mitarbeiter.domain.gruppe.GruppeId;
import io.metis.mitarbeiter.domain.gruppe.GruppeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Repository;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
class KeycloakGruppePersistenceAdapter implements GruppeRepository {

    static final String ATTRIBUTE_DESCRIPTION = "description";
    static final String ATTRIBUTE_GROUP_ID = "group_id";
    static final String ATTRIBUTE_INITIATED_AT = "initiated_at";

    private final Keycloak keycloak;
    private final KeycloakAttributeAccessor attributeAccessor;
    private final KeycloakConfiguration configuration;
    private final KeycloakGruppeMapper mapper;

    @Override
    public Gruppe save(Gruppe gruppe) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupsResource groupsResource = realmResource.groups();

        GroupRepresentation representation = groupsResource.groups().stream()
                .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                .filter(groupRepresentation -> groupRepresentation.getAttributes().get(ATTRIBUTE_GROUP_ID).contains(gruppe.getId().value().toString()))
                .findFirst().orElse(null);

        if (representation == null) {
            if (!Response.Status.Family.SUCCESSFUL.equals(groupsResource.add(mapper.to(gruppe)).getStatusInfo().getFamily())) {
                throw new RuntimeException("unable to create group");
            } else {
                representation = groupsResource.groups().stream()
                        .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                        .filter(groupRepresentation -> groupRepresentation.getAttributes().get(ATTRIBUTE_GROUP_ID).contains(gruppe.getId().value().toString()))
                        .findFirst().orElseThrow(() -> new RuntimeException("unable to find created group"));
            }
        }

        List<RoleRepresentation> roles = new ArrayList<>();
        for (Berechtigungsschluessel berechtigungsschluessel : gruppe.getZugewieseneBerechtigungen()) {
            RolesResource rolesResource = realmResource.roles();
            roles.add(rolesResource.list().stream()
                    .map(roleRepresentation -> rolesResource.get(roleRepresentation.getName()).toRepresentation())
                    .filter(roleRepresentation -> roleRepresentation.getAttributes().containsKey(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_PERMISSION_ID))
                    .filter(roleRepresentation -> roleRepresentation.getAttributes().get(KeycloakBerechtigungPersistenceAdapter.ATTRIBUTE_PERMISSION_ID).contains(berechtigungsschluessel.value()))
                    .findFirst().orElseThrow(() -> new RuntimeException("unable to find role for assigned permission")));
        }

        groupsResource.group(representation.getId()).roles().realmLevel().add(roles);
        return gruppe;
    }

    @Override
    public Optional<Gruppe> findById(GruppeId gruppeId) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupsResource groupsResource = realmResource.groups();
        return groupsResource.groups().stream()
                .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                .filter(groupRepresentation -> groupRepresentation.getAttributes().get(ATTRIBUTE_GROUP_ID).contains(gruppeId.value().toString()))
                .map(mapper::from)
                .findAny();
    }

    @Override
    public boolean existsById(GruppeId gruppeId) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupsResource groupsResource = realmResource.groups();
        return groupsResource.groups().stream()
                .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                .anyMatch(groupRepresentation -> groupRepresentation.getAttributes().get(ATTRIBUTE_GROUP_ID).contains(gruppeId.value().toString()));
    }

    @Override
    public List<Gruppe> findAll() {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupsResource groupsResource = realmResource.groups();
        return groupsResource.groups().stream()
                // to also get the attributes of the role represention we need explicitly load it
                .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                .filter(groupRepresentation -> attributeAccessor.getAttributeValue(groupRepresentation.getAttributes(), ATTRIBUTE_GROUP_ID) != null)
                .map(mapper::from)
                .toList();
    }

    @Override
    public void deleteAll() {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        for (Gruppe gruppe : findAll()) {
            rolesResource.deleteRole(gruppe.getName().value());
        }
    }

    @Override
    public void deleteById(GruppeId gruppeId) {
        findById(gruppeId).ifPresent(gruppe -> {
            RealmResource realmResource = keycloak.realm(configuration.getRealm());
            RolesResource rolesResource = realmResource.roles();
            rolesResource.deleteRole(gruppe.getName().value());
        });
    }

    @Override
    public Optional<Gruppe> findByName(String name) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        GroupsResource groupsResource = realmResource.groups();
        return groupsResource.groups().stream()
                .filter(groupRepresentation -> groupRepresentation.getName().equals(name))
                // to also get the attributes of the role represention we need explicitly load it
                .map(groupRepresentation -> groupsResource.group(groupRepresentation.getId()).toRepresentation())
                .map(mapper::from)
                .findFirst();
    }
}
