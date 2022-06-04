package org.crsoft.cartonplast.users.controller;

import org.crsoft.cartonplast.users.exception.InsertException;
import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.vo.req.UserReq;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * User Controller
 *
 * @author jyepez
 */
@RestController
@RequestMapping(V1_API_VERSION + "/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Create User
     *
     * @param user user
     * @return void
     */
    @PostMapping("")
    @RolesAllowed("backend-admin")
    public ResponseEntity<MessageRes> createUser(@RequestBody UserReq user) {
        try {
            return ResponseEntity.ok().body(this.userService.createUser(user));
        } catch (InsertException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }

    /**
     * Find All Users
     *
     * @return Collection User
     */
    @GetMapping("")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Collection<UserRes>> findAllUsers() {
        try {
            return ResponseEntity.ok().body(this.userService.findAllUsers());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Find User By Id
     *
     * @param id id
     * @return User
     */
    @GetMapping("/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<UserRes> findUserById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(this.userService.findUserById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update User By Id
     *
     * @param id   id
     * @param user user
     * @return void
     */
    @PutMapping("/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> updateUserById(@PathVariable("id") String id, @RequestBody UserReq user) {
        try {
            this.userService.updateUserById(id, user);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }

    /**
     * Delete User By Id
     *
     * @param id id
     * @return void
     */
    @DeleteMapping("/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        try {
            this.userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping("/findUserByUserName/{userName}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<UserRes> findUserByUserName(@PathVariable("userName") String userName) {
        try {
            return ResponseEntity.ok().body(this.userService.findUserByUserName(userName));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
