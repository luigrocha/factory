package org.crsoft.cartonplast.users.controller;

import org.crsoft.cartonplast.users.exception.InsertException;
import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.service.IKeycloakService;
import org.crsoft.cartonplast.users.vo.req.UserReq;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * User Controller
 *
 * @author jyepez
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final IKeycloakService keycloakService;

    public UserController(IKeycloakService keycloakService) {
        this.keycloakService = keycloakService;
    }

    /**
     * Create User
     *
     * @param user user
     * @return void
     */
    @PostMapping("/createUser")
    public ResponseEntity<MessageRes> createUser(@RequestBody UserReq user) {
        try {
            return ResponseEntity.ok().body(this.keycloakService.createUser(user));
        } catch (InsertException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }

    /**
     * Find All Users
     *
     * @return Collection User
     */
    @GetMapping("/findAllUsers")
    public ResponseEntity<Collection<UserRes>> findAllUsers() {
        try {
            return ResponseEntity.ok().body(this.keycloakService.findAllUsers());
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
    @GetMapping("/findUserById/{id}")
    public ResponseEntity<UserRes> findUserById(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok().body(this.keycloakService.findUserById(id));
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
    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable("id") String id, @RequestBody UserReq user) {
        try {
            this.keycloakService.updateUserById(id, user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Delete User By Id
     *
     * @param id id
     * @return void
     */
    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
        try {
            this.keycloakService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }
}
