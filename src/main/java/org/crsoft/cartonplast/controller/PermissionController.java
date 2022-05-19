package org.crsoft.cartonplast.controller;

import org.crsoft.cartonplast.exeption.InsertException;
import org.crsoft.cartonplast.exeption.NotFoundException;
import org.crsoft.cartonplast.service.IPermissionService;
import org.crsoft.cartonplast.vo.req.PermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 19/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION+"/permission")
public class PermissionController {

    private final IPermissionService permissionService;

    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PermissionReq permissionReq) {
        try {
            this.permissionService.create(permissionReq);
            return ResponseEntity.ok().build();
        } catch (InsertException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Collection<PermissionRes>> findPermissionsByMenuCode(@PathVariable("code") Integer code){
        try {
            return ResponseEntity.ok().body(this.permissionService.findPermissionsByMenuCode(code));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
