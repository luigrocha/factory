package org.crsoft.cartonplast.app.controller;

import org.crsoft.cartonplast.app.exeption.InsertException;
import org.crsoft.cartonplast.app.exeption.NotFoundException;
import org.crsoft.cartonplast.app.exeption.UpdateException;
import org.crsoft.cartonplast.app.service.IMenuService;
import org.crsoft.cartonplast.vo.req.MenuReq;
import org.crsoft.cartonplast.vo.res.MenuRes;
import org.crsoft.cartonplast.vo.res.TreeNodeRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 5/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION+"/menu")
public class MenuController {

    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/createItem")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> createItem(@RequestBody MenuReq menuReq) {
        try {
            this.menuService.createItem(menuReq);
            return ResponseEntity.ok().build();
        } catch (InsertException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/findAllItems")
    @RolesAllowed({"backend-admin", "backend-supervisor", "backend-user"})
    public ResponseEntity<Collection<MenuRes>> findAllItems(@RequestBody Collection<String> roles) {
        try {
            return ResponseEntity.ok().body(this.menuService.findAllItems(roles));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateItem/{code}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> updateItem(@PathVariable("code") Integer code, @RequestBody MenuReq menuReq) {
        try {
            this.menuService.updateItem(code, menuReq);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteItem/{code}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> deleteItem(@PathVariable("code") Integer code) {
        try {
            this.menuService.deleteItem(code);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findAllItemsTree")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Collection<TreeNodeRes>> findAllItemsTree() {
        try {
            return ResponseEntity.ok().body(this.menuService.findAllItemsTree());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
