package org.crsoft.users.controller;

import org.crsoft.users.dto.ResponseMessage;
import org.crsoft.users.service.IKeycloakService;
import org.crsoft.users.vo.req.UserReq;
import org.crsoft.users.vo.res.UserRes;
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
    public ResponseEntity<ResponseMessage> createUser(@RequestBody UserReq user) {
        return ResponseEntity.ok().body(this.keycloakService.createUser(user));
    }

    /**
     * Find All Users
     *
     * @return Collection User
     */
    @GetMapping("/findAllUsers")
    public ResponseEntity<Collection<UserRes>> findAllUsers() {
        return ResponseEntity.ok().body(this.keycloakService.findAllUsers());
    }

    /**
     * Find User By Id
     *
     * @param id id
     * @return User
     */
    @GetMapping("/findUserById/{id}")
    public ResponseEntity<UserRes> findUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(this.keycloakService.findUserById(id));
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
        this.keycloakService.updateUserById(id, user);
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
        this.keycloakService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
