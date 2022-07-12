package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.ColorAService;
import org.crsoft.cartonplast.vo.req.ColorAReq;
import org.crsoft.cartonplast.vo.res.ColorARes;
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
@RequestMapping(V1_API_VERSION + "/colors-a")
@RequiredArgsConstructor
public class ColorAController {

    private final ColorAService colorAService;

    @GetMapping
    public ResponseEntity<Collection<ColorARes>> getAllColorsA() {
        return ResponseEntity.ok(this.colorAService.findAllValidColors());
    }

    @PostMapping
    public ResponseEntity<ColorARes> createColorA(
            @Valid @RequestBody ColorAReq colorAReq) {
        return new ResponseEntity<>(this.colorAService.createColorA(colorAReq),
                HttpStatus.CREATED);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ColorARes> findColorAByCode(
            @PathVariable("code") String code) {
        return ResponseEntity.ok(this.colorAService.findColorAByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<ColorARes> updateColorAByCode(
            @PathVariable("code") String code,
            @Valid @RequestBody ColorAReq colorA) {
        return new ResponseEntity<>(
                this.colorAService.updateColorAByCode(code, colorA),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deleteColorAByCode(
            @PathVariable("code") String code) {
        return new ResponseEntity<>(
                this.colorAService.deleteColorAByCode(code),
                HttpStatus.OK
        );
    }

}
