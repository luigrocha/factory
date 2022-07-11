package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.IColorCatalogService;
import org.crsoft.cartonplast.vo.req.ColorCatalogReq;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 30/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/color-catalog")
@RequiredArgsConstructor
public class ColorCatalogController {

    private final IColorCatalogService colorCatalogService;

    @GetMapping
    public ResponseEntity<Collection<ColorCatalogRes>> findAllValidColors() {
        return ResponseEntity.ok(this.colorCatalogService.findAllValidColors());
    }

    @PostMapping
    public ResponseEntity<ColorCatalogRes> createColorCatalog(
            @Valid @RequestBody ColorCatalogReq colorCatalogReq) {
        return ResponseEntity.ok(this.colorCatalogService.createColorCatalog(colorCatalogReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorCatalogRes> findColorCatalogByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok(this.colorCatalogService.findColorCatalogByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<ColorCatalogRes> updateColorCatalogByCode(
            @PathVariable("code") Integer code,
            @RequestBody ColorCatalogReq colorCatalogReq) {
        return ResponseEntity.ok(this.colorCatalogService.updateColorCatalogByCode(code, colorCatalogReq));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deleteColorCatalogByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok(this.colorCatalogService.deleteColorCatalogByCode(code));
    }

}
