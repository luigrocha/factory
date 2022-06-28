package org.crsoft.cartonplast.users.controller;

import org.crsoft.cartonplast.users.model.Role;
import org.crsoft.cartonplast.users.service.IRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * Role Controller
 *
 * @author jyepez on 28/4/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/roles")
public class RoleController {

    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Find All Role
     *
     * @return Collection Role
     */
    @GetMapping("")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Collection<Role>> findAllRole() {
        return ResponseEntity.ok().body(this.roleService.findAllRole());
    }

    /**
     * Find Role By Name
     *
     * @param name name
     * @return Role
     */
    @GetMapping("/findRoleByName/{name}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Role> findRoleByName(@PathVariable("name") String name) {
        return ResponseEntity.ok().body(this.roleService.findRoleByName(name));
    }

    /**
     * Add Roles User
     *
     * @param userId userId
     * @param roles  roles
     * @return Status
     */
    @PatchMapping("/addRolesUser/{userId}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Void> addRolesUser(
            @PathVariable("userId") String userId,
            @RequestBody Collection<Role> roles) {
        this.roleService.addRolesUser(userId, roles);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove Roles User
     *
     * @param userId userId
     * @param roles  roles
     * @return Status
     */
    @PatchMapping("/removeRolesUser/{userId}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Void> removeRolesUser(
            @PathVariable("userId") String userId,
            @RequestBody Collection<Role> roles) {
        this.roleService.removeRolesUser(userId, roles);
        return ResponseEntity.ok().build();
    }
}
