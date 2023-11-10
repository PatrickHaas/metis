package io.metis.mitarbeiter.adapters.persistence.berechtigung;

import io.metis.mitarbeiter.adapters.persistence.KeycloakAttributeAccessor;
import io.metis.mitarbeiter.adapters.persistence.KeycloakConfiguration;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigung;
import io.metis.mitarbeiter.domain.berechtigung.BerechtigungRepository;
import io.metis.mitarbeiter.domain.berechtigung.Berechtigungsschluessel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KeycloakBerechtigungPersistenceAdapter implements BerechtigungRepository {

    public static final String ATTRIBUTE_PERMISSION_ID = "permission_id";
    static final String ATTRIBUTE_INITIATED_AT = "initiated_at";

    private final Keycloak keycloak;
    private final KeycloakAttributeAccessor attributeAccessor;
    private final KeycloakConfiguration configuration;
    private final KeycloakBerechtigungMapper mapper;

    @Override
    public Berechtigung save(Berechtigung berechtigung) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        if (findById(berechtigung.getSchluessel()).isEmpty()) {
            rolesResource.create(mapper.to(berechtigung));
        } else {
            log.warn("skipped permission creation, permission {} does already exist", berechtigung.getId().value());
        }
        return berechtigung;
    }

    @Override
    public Optional<Berechtigung> findById(Berechtigungsschluessel berechtigungsschluessel) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        return rolesResource.list().stream()
                .map(roleRepresentation -> rolesResource.get(roleRepresentation.getName()).toRepresentation())
                .filter(roleRepresentation -> roleRepresentation.getAttributes().containsKey(ATTRIBUTE_PERMISSION_ID))
                .filter(roleRepresentation -> roleRepresentation.getAttributes().get(ATTRIBUTE_PERMISSION_ID).contains(berechtigungsschluessel.value()))
                .map(mapper::from)
                .findAny();
    }

    @Override
    public boolean existsById(Berechtigungsschluessel berechtigungsschluessel) {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        return rolesResource.list().stream()
                .map(roleRepresentation -> rolesResource.get(roleRepresentation.getName()).toRepresentation())
                .filter(roleRepresentation -> roleRepresentation.getAttributes().containsKey(ATTRIBUTE_PERMISSION_ID))
                .anyMatch(roleRepresentation -> roleRepresentation.getAttributes().get(ATTRIBUTE_PERMISSION_ID).contains(berechtigungsschluessel.value()));
    }

    @Override
    public List<Berechtigung> findAll() {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        return rolesResource.list().stream()
                // to also get the attributes of the role represention we need explicitly load it
                .map(roleRepresentation -> rolesResource.get(roleRepresentation.getName()).toRepresentation())
                .filter(roleRepresentation -> attributeAccessor.getAttributeValue(roleRepresentation.getAttributes(), ATTRIBUTE_PERMISSION_ID) != null)
                .map(mapper::from)
                .toList();
    }

    @Override
    public void deleteAll() {
        RealmResource realmResource = keycloak.realm(configuration.getRealm());
        RolesResource rolesResource = realmResource.roles();
        for (Berechtigung berechtigung : findAll()) {
            rolesResource.deleteRole(berechtigung.getId().value());
        }
    }

    @Override
    public void deleteById(Berechtigungsschluessel berechtigungsschluessel) {
        findById(berechtigungsschluessel).ifPresent(berechtigung -> {
            RealmResource realmResource = keycloak.realm(configuration.getRealm());
            RolesResource rolesResource = realmResource.roles();
            rolesResource.deleteRole(berechtigung.getId().value());
        });
    }
}
