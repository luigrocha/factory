package org.crsoft.cartonplast.menu.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.menu.service.IMenuService;
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
@RequestMapping(V1_API_VERSION + "/menus")
public class MenuController {

    private final IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/createItem")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> createItem(@RequestBody MenuReq menuReq) throws InsertException {
        this.menuService.createItem(menuReq);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/findAllItems")
    @RolesAllowed({"backend-admin", "backend-supervisor", "backend-user"})
    public ResponseEntity<Collection<MenuRes>> findAllItems(@RequestBody Collection<String> roles) throws NotFoundException {
        return ResponseEntity.ok().body(this.menuService.findAllItems(roles));
    }

    @PutMapping("/updateItem/{code}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> updateItem(@PathVariable("code") Integer code, @RequestBody MenuReq menuReq) throws NotFoundException, UpdateException {
        this.menuService.updateItem(code, menuReq);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteItem/{code}")
    @RolesAllowed("backend-admin")
    public ResponseEntity<?> deleteItem(@PathVariable("code") Integer code) throws NotFoundException, UpdateException {
        this.menuService.deleteItem(code);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findAllItemsTree")
    @RolesAllowed("backend-admin")
    public ResponseEntity<Collection<TreeNodeRes>> findAllItemsTree() throws NotFoundException {
        return ResponseEntity.ok().body(this.menuService.findAllItemsTree());
    }
}
