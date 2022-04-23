package org.crsoft.users.service;

import org.crsoft.users.vo.req.UserReq;
import org.crsoft.users.vo.res.UserRes;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
@Service
public interface IKeycloakService {

    /**
     * Create User
     *
     * @param user user
     */
    void createUser(UserReq user);

    /**
     * Find All Users
     *
     * @return Collection User
     */
    Collection<UserRes> findAllUsers();

    /**
     * Find User By Id
     *
     * @param id id
     * @return User
     */
    UserRes findUserById(String id);

    /**
     * Update User By Id
     *
     * @param id   id
     * @param user user
     */
    void updateUserById(String id, UserReq user);

    /**
     * Delete User By Id
     *
     * @param id
     */
    void deleteUserById(String id);

}
