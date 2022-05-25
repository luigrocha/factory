package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.service.impl.ColorBService;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/colors-b")
public class ColorBController {

    private final ColorBService colorBService;

    public ColorBController(ColorBService colorBService) {
        this.colorBService = colorBService;
    }

    @GetMapping
    public ResponseEntity<Collection<ColorBRes>> getAllColorsB() {
        try {
            return ResponseEntity.ok(this.colorBService.findAllValidColors());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createColorB(@RequestBody ColorB colorB, @RequestHeader("userName") String userName) {
        try {
            colorB.setCreatedBy(userName);
            this.colorBService.createColorB(colorB);
            return ResponseEntity.ok().build();
        } catch (InsertException e) {
            return ResponseEntity.badRequest().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorBRes> findColorBByCode(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok().body(this.colorBService.findColorBByCode(code));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateColorBByCode(@PathVariable("code") String code, @RequestBody ColorB color, @RequestHeader("userName") String userName) {
        try {
            color.setUpdatedBy(userName);
            this.colorBService.updateColorBByCode(code, color);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> deleteColorBByCode(@PathVariable("code") String code, @RequestHeader("userName") String userName) {
        try {
            this.colorBService.deleteColorBByCode(code, userName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
