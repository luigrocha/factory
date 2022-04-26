package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.exception.InsertException;
import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.vo.req.UserReq;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;

import java.util.Collection;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
public interface IKeycloakService {

    /**
     * Create User
     *
     * @param user user
     * @throws InsertException Insert Exception
     */
    MessageRes createUser(UserReq user) throws InsertException;

    /**
     * Find All Users
     *
     * @return Collection User
     * @throws NotFoundException Not Found Exception
     */
    Collection<UserRes> findAllUsers() throws NotFoundException;

    /**
     * Find User By Id
     *
     * @param id id
     * @return User
     * @throws NotFoundException Not Found Exception
     */
    UserRes findUserById(String id) throws NotFoundException;

    /**
     * Update User By Id
     *
     * @param id   id
     * @param user user
     * @throws NotFoundException Not Found Exception
     * @throws UpdateException   Update Exception
     */
    void updateUserById(String id, UserReq user) throws NotFoundException, UpdateException;

    /**
     * Delete User By Id
     *
     * @param id id
     * @throws NotFoundException Not Found Exception
     * @throws UpdateException   Update Exception
     */
    void deleteUserById(String id) throws NotFoundException, UpdateException;

}
