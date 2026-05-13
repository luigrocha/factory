package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.GenerateUsernameReq;
import org.crsoft.cartonplast.users.vo.req.CreateUserReq;
import org.crsoft.cartonplast.users.vo.req.UpdateUserReq;
import org.crsoft.cartonplast.users.vo.res.GenerateUsernameRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;

import java.util.Collection;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
public interface IUserService {

    /**
     * Create User
     *
     * @param user user
     */
    UserRes createUser(CreateUserReq user);

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
    void updateUserById(String id, UpdateUserReq user);

    /**
     * Delete User By Id
     *
     * @param id id
     */
    void deleteUserById(String id);

    /**
     * Find User By User Name
     *
     * @param userName userName
     * @return User Res
     */
    UserRes findUserByUserName(String userName);

    GenerateUsernameRes generateUsername(GenerateUsernameReq req);

    boolean existsByEmail(String email);
}
