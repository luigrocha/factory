package org.crsoft.cartonplast.users.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.cartonplast.users.exception.BusinessException;
import org.crsoft.cartonplast.users.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.users.model.Role;
import org.crsoft.cartonplast.users.service.IRoleService;
import org.crsoft.cartonplast.users.util.KeycloakUtil;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Role Service
 *
 * @author jyepez on 27/4/2022
 */
@Service
@Log4j2
public class RoleService implements IRoleService {

    private final KeycloakUtil keycloakUtil;

    private final UserService userService;

    public RoleService(KeycloakUtil keycloakUtil, UserService userService) {
        this.keycloakUtil = keycloakUtil;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Role> findAllRole() {
        Collection<Role> roles = new ArrayList<>(0);
        for (RoleRepresentation roleRepresentation : getRolesResource().list()) {
            if (roleRepresentation.getName().contains("realm")) {
                roles.add(buildRole(roleRepresentation));
            }
        }
        return roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role findRoleByName(String name) {
        RoleRepresentation roleRepresentation = getRoleRepresentationByName(name);

        return buildRole(roleRepresentation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRolesUser(String userId, Collection<Role> roles) {
        addOrRemoveRoleUser(userId, roles, Boolean.TRUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRolesUser(String userId, Collection<Role> roles) {
        addOrRemoveRoleUser(userId, roles, Boolean.FALSE);
    }

    /**
     * Add Or Remove RoleUser
     *
     * @param userId userId
     * @param roles  roles
     * @param option option
     */
    private void addOrRemoveRoleUser(String userId, Collection<Role> roles, Boolean option) {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>(0);
        UserRepresentation userRepresentation = userService.getUserRepresentationById(userId);
        try {
            for (Role role : roles) {
                RoleRepresentation roleRepresentation = getRoleRepresentationByName(role.getName());
                roleRepresentations.add(roleRepresentation);
            }
            if (Boolean.TRUE.equals(option)) {
                keycloakUtil.getRealmResource().users()
                        .get(userRepresentation.getId()).roles().realmLevel().add(roleRepresentations);
            } else {
                keycloakUtil.getRealmResource().users()
                        .get(userRepresentation.getId()).roles().realmLevel().remove(roleRepresentations);
            }
        } catch (Exception e) {
            log.error("addOrRemoveRoleUser Error: {}", e.getMessage());
            throw new BusinessException(BusinessExceptionReason.ROLE_USER_UPDATE_FAILED);
        }
    }

    /**
     * Build Role
     *
     * @param roleRepresentation roleRepresentation
     * @return Role
     */
    private Role buildRole(RoleRepresentation roleRepresentation) {
        return Role.builder()
                .id(roleRepresentation.getId())
                .name(roleRepresentation.getName())
                .description(roleRepresentation.getDescription())
                .build();
    }

    /**
     * Get Role Representation By Name
     *
     * @param name name
     * @return RoleRepresentation
     */
    private RoleRepresentation getRoleRepresentationByName(String name) {
        try {
            return getRolesResource().get(name).toRepresentation();
        } catch (Exception e) {
            log.error("getRoleRepresentationByName Not Found Role {}", name);
            throw new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND, name);
        }
    }

    /**
     * Get Roles Resource
     *
     * @return RolesResource
     */
    private RolesResource getRolesResource() {
        return keycloakUtil.getRealmResource().roles();
    }

}
