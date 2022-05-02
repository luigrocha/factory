package org.crsoft.cartonplast.users.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.cartonplast.users.enums.StatusKeycloakEnum;
import org.crsoft.cartonplast.users.exception.InsertException;
import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.util.KeycloakUtil;
import org.crsoft.cartonplast.users.vo.req.UserReq;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
@Service
@Log4j2
public class UserService implements IUserService {

    private final KeycloakUtil keycloakUtil;

    public UserService(KeycloakUtil keycloakUtil) {
        this.keycloakUtil = keycloakUtil;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageRes createUser(UserReq user) throws InsertException {
        MessageRes messageRes = MessageRes.builder().build();
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
            messageRes.setStatus(status);

            if (StatusKeycloakEnum.OK.getCode().equals(status)) {
                String path = response.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf("/") + 1);
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(Boolean.FALSE);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());
                usersResource.get(userId).resetPassword(credentialRepresentation);

                RoleRepresentation roleRepresentation = keycloakUtil.getRealmResource().roles().get("realm-user").toRepresentation();
                keycloakUtil.getRealmResource().users().get(userId).roles().realmLevel().add(List.of(roleRepresentation));

                messageRes.setMessage(user.getUserName() + " usuario creado");
                log.info("{} user created", user.getUserName());

            } else if (StatusKeycloakEnum.EXIST.getCode().equals(status)) {
                messageRes.setMessage(user.getUserName() + " ya existe");
                log.info("{} already exists", user.getUserName());
            } else {
                messageRes.setMessage("Error creando usuario, contacte al administrador");
                log.info("Error creating user");
            }
            return messageRes;
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getCause().getMessage());
            throw new InsertException("createUser", "Error creando usuario, contacte al administrador ");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<UserRes> findAllUsers() throws NotFoundException {
        try {
            Collection<UserRes> users = new ArrayList<>(0);
            Collection<UserRepresentation> userRepresentations = getUsersResource().list();

            for (UserRepresentation userRepresentation : userRepresentations) {
                users.add(buildUserRes(userRepresentation));
            }
            return users;
        } catch (Exception e) {
            log.error("findAllUsers Not Found");
            throw new NotFoundException("No existen datos");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRes findUserById(String id) throws NotFoundException {
        UserRepresentation userRepresentation = getUserRepresentationById(id);
        return buildUserRes(userRepresentation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserById(String id, UserReq user) throws UpdateException, NotFoundException {
        UserRepresentation userRepresentation = getUserRepresentationById(id);
        try {
            userRepresentation.setUsername(user.getUserName());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());

            keycloakUtil.getRealmResource().users().get(id).update(userRepresentation);
        } catch (Exception e) {
            log.error("findUserById Not Found Id {}", id);
            throw new UpdateException("updateUserById", "No se puede actualizar usuario");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserById(String id) throws NotFoundException, UpdateException {
        UserRepresentation userRepresentation = getUserRepresentationById(id);
        try {
            getUsersResource().delete(userRepresentation.getId());
        } catch (Exception e) {
            throw new UpdateException("deleteUserById", "No se puede eliminar usuario");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRes findUserByUserName(String userName) throws NotFoundException {
        Collection<UserRepresentation> userRepresentations = getUsersResource().list();
        UserRepresentation userRepresentation = userRepresentations.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findAny()
                .orElse(null);
        if(Objects.nonNull(userRepresentation)){
            return  buildUserRes(userRepresentation);
        }else{
            throw new NotFoundException("No existe usuario");
        }
    }

    /**
     * Build User Res
     *
     * @param userRepresentation userRepresentation
     * @return UserRes
     */
    private UserRes buildUserRes(UserRepresentation userRepresentation) {
        return UserRes.builder()
                .id(userRepresentation.getId())
                .userName(userRepresentation.getUsername())
                .email(userRepresentation.getEmail())
                .firstName(userRepresentation.getFirstName())
                .lastName(userRepresentation.getLastName())
                .roles(getRolesByUserId(userRepresentation.getId()))
                .build();
    }

    /**
     * Get User Representation By ID
     *
     * @param id id
     * @return UserRepresentation
     * @throws NotFoundException Not Found Exception
     */
    public UserRepresentation getUserRepresentationById(String id) throws NotFoundException {
        try {
            RealmResource realmResource = keycloakUtil.getRealmResource();
            return realmResource.users().get(id).toRepresentation();
        } catch (Exception e) {
            log.error("getUserRepresentationById Not Found Id {}", id);
            throw new NotFoundException("No existe usuario");
        }
    }


    /**
     * Get Users Resource
     *
     * @return UsersResource
     */
    private UsersResource getUsersResource() {
        return keycloakUtil.getRealmResource().users();
    }

    /**
     * Get Roles By User ID
     *
     * @param userId userId
     * @return Collection String
     */
    private Collection<String> getRolesByUserId(String userId) {
        Collection<String> roles = new ArrayList<>();
        Collection<RoleRepresentation> roleRepresentations = keycloakUtil.getRealmResource()
                .users().get(userId).roles().realmLevel().listAll();
        for (RoleRepresentation roleRepresentation : roleRepresentations) {
            if(!roleRepresentation.getName().equals("default-roles-tutorial"))
            roles.add(roleRepresentation.getName());
        }
        return roles;
    }
}
