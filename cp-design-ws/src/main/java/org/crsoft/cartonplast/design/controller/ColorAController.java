package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.ColorAService;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<ColorARes>> getAllColorsA() {
        return ResponseEntity.ok(this.colorAService.findAllValidColors());
    }
}
