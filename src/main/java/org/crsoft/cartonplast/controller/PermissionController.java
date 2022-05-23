package org.crsoft.cartonplast.controller;

import org.crsoft.cartonplast.exeption.NotFoundException;
import org.crsoft.cartonplast.exeption.UpdateException;
import org.crsoft.cartonplast.service.IPermissionService;
import org.crsoft.cartonplast.vo.req.PermissionsPageReq;
import org.crsoft.cartonplast.vo.req.TypePermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;
import org.crsoft.cartonplast.vo.res.TypePermissionRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 19/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/permission")
public class PermissionController {

    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Collection<PermissionRes>> findPermissionsByMenuCode(@PathVariable("code") Integer code) {
        try {
            return ResponseEntity.ok().body(this.permissionService.findPermissionsByMenuCode(code));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{codeMenu}/{codePermission}")
    public ResponseEntity<?> updatePermissionByMenuCode(@RequestBody Collection<TypePermissionReq> typePermissionReqs, @PathVariable("codeMenu") Integer codeMenu, @PathVariable("codePermission") Integer codePermission) {
        try {
            this.permissionService.updatePermissionByMenuCode(typePermissionReqs, codeMenu, codePermission);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/findPermissionsPage")
    public ResponseEntity<Collection<TypePermissionRes>> findPermissionsPage(@RequestBody PermissionsPageReq permissionsPageReq) {
        try {
            return ResponseEntity.ok().body(permissionService.findPermissionsPage(permissionsPageReq.getUrl(), permissionsPageReq.getRoles()));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
