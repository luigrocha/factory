package org.crsoft.cartonplast.users.util;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author jyepez on 27/4/2022
 */

@Service
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

}
