package org.crsoft.cartonplast.users.controller;

import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.model.Role;
import org.crsoft.cartonplast.users.service.IRoleService;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

import static org.crsoft.cartonplast.users.util.Constants.*;

/**
 * Role Controller
 *
 * @author jyepez on 28/4/2022
 */
@RestController
@RequestMapping("/role")
@CrossOrigin(origins = {CROSS_LOCAL, CROSS_DEVELOP, CROSS_PRODUCTION})
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
    @GetMapping("/findAllRole")
    @RolesAllowed("realm-admin")
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
    @RolesAllowed("realm-admin")
    public ResponseEntity<Role> findRoleByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.ok().body(this.roleService.findRoleByName(name));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add Roles User
     *
     * @param userId userId
     * @param roles  roles
     * @return Status
     */
    @PatchMapping("/addRolesUser/{userId}")
    @RolesAllowed("realm-admin")
    public ResponseEntity<?> addRolesUser(@PathVariable("userId") String userId, @RequestBody Collection<Role> roles) {
        try {
            this.roleService.addRolesUser(userId, roles);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }

    /**
     * Remove Roles User
     *
     * @param userId userId
     * @param roles  roles
     * @return Status
     */
    @PatchMapping("/removeRolesUser/{userId}")
    @RolesAllowed("realm-admin")
    public ResponseEntity<?> removeRolesUser(@PathVariable("userId") String userId, @RequestBody Collection<Role> roles) {
        try {
            this.roleService.removeRolesUser(userId, roles);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }
}
