package org.crsoft.cartonplast.users.util;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author jyepez on 27/4/2022
 */

@Component
public class KeycloakUtil {

    private static RealmResource realmResource;
    @Value("${keycloak.auth-server-url}")
    private String serverUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${token-adm}")
    private String token;

    /**
     * Get Realm Resource
     *
     * @return RealmResource
     */
    public RealmResource getRealmResource() {
        if (Objects.isNull(realmResource)) {
            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm("master")
                    .username("admin")
                    .password(token)
                    .clientId("admin-cli")
                    .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                    .build();
            realmResource = keycloak.realm(realm);
        }
        return realmResource;
    }

    @Named("getRolesByUserId")
    public List<String> getRolesByUserId(String userId) {
        List<String> roles = new ArrayList<>();
        List<RoleRepresentation> roleRepresentations = this.getRealmResource()
                .users().get(userId).roles().realmLevel().listAll();
        for (RoleRepresentation roleRepresentation : roleRepresentations) {
            if (!roleRepresentation.getName().equals("default-roles-tutorial"))
                roles.add(roleRepresentation.getName());
        }
        return roles;
    }
}
