package org.crsoft.cartonplast.menu.controller;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.menu.service.IPermissionService;
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
@RequestMapping(V1_API_VERSION + "/permissions")
public class PermissionController {

    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Collection<PermissionRes>> findPermissionsByMenuCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok().body(this.permissionService.findPermissionsByMenuCode(code));
    }

    @PatchMapping("/{codeMenu}/{codePermission}")
    public ResponseEntity<?> updatePermissionByMenuCode(@RequestBody Collection<TypePermissionReq> typePermissionReqs, @PathVariable("codeMenu") Integer codeMenu, @PathVariable("codePermission") Integer codePermission) throws NotFoundException, UpdateException {
        this.permissionService.updatePermissionByMenuCode(typePermissionReqs, codeMenu, codePermission);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/findPermissionsPage")
    public ResponseEntity<Collection<TypePermissionRes>> findPermissionsPage(@RequestBody PermissionsPageReq permissionsPageReq) throws NotFoundException {
        return ResponseEntity.ok().body(permissionService.findPermissionsPage(permissionsPageReq.getUrl(), permissionsPageReq.getRoles()));
    }
}
