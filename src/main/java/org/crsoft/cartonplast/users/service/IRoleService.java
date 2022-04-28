package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
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
     * @throws NotFoundException Not Found Exception
     */
    Role findRoleByName(String id) throws NotFoundException;

    /**
     * Add Role User
     *
     * @param userId userId
     * @param roles  roles
     * @throws NotFoundException Not Found Exception
     * @throws UpdateException   Update Exception
     */
    void addRolesUser(String userId, Collection<Role> roles) throws NotFoundException, UpdateException;

    /**
     * Remove Role User
     *
     * @param userId userId
     * @param roles  roles
     * @throws NotFoundException Not Found Exception
     * @throws UpdateException   Update Exception
     */
    void removeRolesUser(String userId, Collection<Role> roles) throws NotFoundException, UpdateException;
}
