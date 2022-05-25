package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.service.impl.ColorAService;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/colors-a")
public class ColorAController {

    private final ColorAService colorAService;

    public ColorAController(ColorAService colorAService) {
        this.colorAService = colorAService;
    }

    @GetMapping
    public ResponseEntity<Collection<ColorARes>> getAllColorsA() {
        try {
            return ResponseEntity.ok(this.colorAService.findAllValidColors());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createColorA(@RequestBody ColorA colorA, @RequestHeader("userName") String userName) {
        try {
            colorA.setCreatedBy(userName);
            this.colorAService.createColorA(colorA);
            return ResponseEntity.ok().build();
        } catch (InsertException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorARes> findColorAByCode(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok(this.colorAService.findColorAByCode(code));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateColorAByCode(@PathVariable("code") String code, @RequestBody ColorA colorA, @RequestHeader("userName") String userName) {
        try {
            colorA.setUpdatedBy(userName);
            this.colorAService.updateColorAByCode(code, colorA);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteColorAByCode(@PathVariable("code") String code, @RequestHeader("userName") String userName) {
        try {
            this.colorAService.deleteColorAByCode(code, userName);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
