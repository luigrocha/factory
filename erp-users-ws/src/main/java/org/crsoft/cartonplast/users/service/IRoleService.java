package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.model.Role;

import java.util.Collection;

/**
 * Role Service
 *
 * @author jyepez on 27/4/2022
 */
public interface IRoleService {

    /**
     * @return Collection Role
     */
    Collection<Role> findAllRole();

    /**
     * Find Role By Name
     *
     * @param id id
     * @return Role
     */
    Role findRoleByName(String id);

    /**
     * Add Role User
     *
     * @param userId userId
     * @param roles  roles
     */
    void addRolesUser(String userId, Collection<Role> roles);

    /**
     * Remove Role User
     *
     * @param userId userId
     * @param roles  roles
     */
    void removeRolesUser(String userId, Collection<Role> roles);
}
