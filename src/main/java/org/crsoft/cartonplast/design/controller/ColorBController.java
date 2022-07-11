package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.ColorBService;
import org.crsoft.cartonplast.vo.req.ColorBReq;
import org.crsoft.cartonplast.vo.res.ColorBRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/colors-b")
@RequiredArgsConstructor
public class ColorBController {

    private final ColorBService colorBService;

    @GetMapping
    public ResponseEntity<Collection<ColorBRes>> getAllColorsB() {
        return ResponseEntity.ok(this.colorBService.findAllValidColors());
    }

    @PostMapping
    public ResponseEntity<ColorBRes> createColorB(
            @Valid @RequestBody ColorBReq colorB) {
        return new ResponseEntity<>(
                this.colorBService.createColorB(colorB),
                HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorBRes> findColorBByCode(
            @PathVariable("code") String code) {
        return new ResponseEntity<>(
                this.colorBService.findColorBByCode(code),
                HttpStatus.OK);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<ColorBRes> updateColorBByCode(
            @Valid @PathVariable("code") String code,
            @RequestBody ColorBReq color) {
        return new ResponseEntity<>(
                this.colorBService.updateColorBByCode(code, color),
                HttpStatus.OK);
    }

    @DeleteMapping("{code}")
    public ResponseEntity<Boolean> deleteColorBByCode(
            @PathVariable("code") String code) {
        return new ResponseEntity<>(
                this.colorBService.deleteColorBByCode(code),
                HttpStatus.OK);
    }
}
