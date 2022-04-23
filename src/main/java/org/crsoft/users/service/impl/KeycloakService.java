package org.crsoft.users.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.users.enums.StatusKeycloakEnum;
import org.crsoft.users.service.IKeycloakService;
import org.crsoft.users.vo.req.UserReq;
import org.crsoft.users.vo.res.UserRes;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
@Log4j2
public class KeycloakService implements IKeycloakService {

    @Value("${keycloak.auth-server-url}")
    private String serviceUrl;

    @Value("${keycloak.realm}")
    private String realm;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUser(UserReq user) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<UserRes> findAllUsers() {
        Collection<UserRes> users = new ArrayList<>(0);
        Collection<UserRepresentation> userRepresentations = getUsersResource().list();
        for (UserRepresentation userRepresentation : userRepresentations) {
            users.add(
                    UserRes.builder()
                            .id(userRepresentation.getId())
                            .userName(userRepresentation.getUsername())
                            .email(userRepresentation.getEmail())
                            .firstName(userRepresentation.getFirstName())
                            .lastName(userRepresentation.getLastName())
                            .roles(userRepresentation.getRealmRoles())
                            .build()
            );
        }
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRes findUserById(String id) {
        RealmResource realmResource = getRealmResource();
        UserRepresentation userRepresentation = realmResource.users().get(id).toRepresentation();
        return UserRes.builder()
                .id(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .roles(userRepresentation.getRealmRoles())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserById(String id, UserReq user) {
        RealmResource realmResource = getRealmResource();
        UserRepresentation userRepresentation = realmResource.users().get(id).toRepresentation();

        userRepresentation.setUsername(user.getUserName());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());

        realmResource.users().get(id).update(userRepresentation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserById(String id) {
        RealmResource realmResource = getRealmResource();
        realmResource.users().get(id).remove();
    }


    /**
     * Get Realm Resource
     *
     * @return RealmResource
     */
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

    /**
     * Get Users Resource
     *
     * @return UsersResource
     */
    private UsersResource getUsersResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}
