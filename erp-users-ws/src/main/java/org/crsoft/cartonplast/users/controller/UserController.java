package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.vo.req.CreateUserReq;
import org.crsoft.cartonplast.users.vo.req.GenerateUsernameReq;
import org.crsoft.cartonplast.users.vo.req.UpdateUserReq;
import org.crsoft.cartonplast.users.vo.res.GenerateUsernameRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Collection;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * User Controller
 *
 * @author jyepez
 */
@RestController
@RequestMapping(V1_API_VERSION + "/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    /**
     * Create User
     *
     * @param user user
     * @return void
     */
    @PostMapping("")
    @RolesAllowed("backend-admin")
    public ResponseEntity<UserRes> createUser(@Valid @RequestBody CreateUserReq user) {
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    /**
     * Find All Users
     *
     * @return Collection User
     */
    @GetMapping("")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Collection<UserRes>> findAllUsers() {
        return ResponseEntity.ok().body(this.userService.findAllUsers());
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
        return ResponseEntity.ok().body(this.userService.findUserById(id));
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
    public ResponseEntity<Void> updateUserById(@PathVariable("id") String id,
                                               @Valid @RequestBody UpdateUserReq user) {
        this.userService.updateUserById(id, user);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete User By Id
     *
     * @param id id
     * @return void
     */
    @DeleteMapping("/{id}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") String id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findUserByUserName/{userName}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<UserRes> findUserByUserName(@PathVariable("userName") String userName) {
        return ResponseEntity.ok().body(this.userService.findUserByUserName(userName));
    }

    @PostMapping("/generate-username")
    @RolesAllowed("backend-admin")
    public ResponseEntity<GenerateUsernameRes> generateUsername(
            @RequestBody GenerateUsernameReq req) {
        return ResponseEntity.ok().body(this.userService.generateUsername(req));
    }

    @GetMapping("/exists-by-email/{email}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Boolean> existsByEmail(
            @PathVariable("email") String email) {
        return ResponseEntity.ok().body(this.userService.existsByEmail(email));
    }
}
