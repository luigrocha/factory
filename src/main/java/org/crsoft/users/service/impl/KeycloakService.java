package org.crsoft.users.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.users.enums.StatusKeycloakEnum;
import org.crsoft.users.model.User;
import org.crsoft.users.service.IKeycloakService;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author jyepez
 */
@Log4j2
public class KeycloakService implements IKeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serviceUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void createUser(User user) {
        try {
            UsersResource usersResource = getUsersResource();
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(user.getUserName());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());
            userRepresentation.setEnabled(Boolean.TRUE);

            Response response = usersResource.create(userRepresentation);
            Integer status = response.getStatus();

            if (StatusKeycloakEnum.OK.getCode().equals(status)) {
                String path = response.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf("/") + 1);
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(Boolean.FALSE);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());
                usersResource.get(userId).resetPassword(credentialRepresentation);

                RealmResource realmResource = getRealmResource();
                RoleRepresentation roleRepresentation = realmResource.roles().get("realm-user").toRepresentation();
                realmResource.users().get(userId).roles().realmLevel().add(List.of(roleRepresentation));

                log.info("{} user created", user.getUserName());

            } else if (StatusKeycloakEnum.EXIST.getCode().equals(status)) {
                log.info("{} exists yet", user.getUserName());
            } else {
                log.info("Error creating user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RealmResource getRealmResource() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serviceUrl)
                .realm("master")
                .username("admin")
                .password("admin")
                .clientId("admin-cli")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
        return keycloak.realm(realm);
    }

    private UsersResource getUsersResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
