package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.design.service.IColorCatalogService;
import org.crsoft.cartonplast.design.vo.res.ColorCatalogRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 30/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/colorCatalog")
public class ColorCatalogController {

    private final IColorCatalogService colorCatalogService;

    public ColorCatalogController(IColorCatalogService colorCatalogService) {
        this.colorCatalogService = colorCatalogService;
    }

    @GetMapping
    public ResponseEntity<Collection<ColorCatalogRes>> findAllValidColors() throws NotFoundException {
        return ResponseEntity.ok(this.colorCatalogService.findAllValidColors());
    }

    @PostMapping
    public ResponseEntity<?> createColorCatalog(@RequestBody ColorCatalog colorCatalog, @RequestHeader("userName") String userName) throws InsertException {
        colorCatalog.setCreatedBy(userName);
        this.colorCatalogService.createColorCatalog(colorCatalog);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorCatalogRes> findColorCatalogByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.colorCatalogService.findColorCatalogByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateColorCatalogByCode(
            @PathVariable("code") Integer code,
            @RequestBody ColorCatalog colorCatalog,
            @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        colorCatalog.setUpdatedBy(userName);
        this.colorCatalogService.updateColorCatalogByCode(code,colorCatalog);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteColorCatalogByCode(@PathVariable("code") Integer code, @RequestHeader("userName") String userName)
            throws NotFoundException, UpdateException {
        this.colorCatalogService.deleteColorCatalogByCode(code,userName);
        return ResponseEntity.ok().build();
    }

}
