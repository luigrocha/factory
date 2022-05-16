package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.ColorBService;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
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
@RequestMapping(V1_API_VERSION + "/colors-b")
public class ColorBController {

    private final ColorBService colorBService;

    public ColorBController(ColorBService colorBService) {
        this.colorBService = colorBService;
    }

    @GetMapping
    public ResponseEntity<List<ColorBRes>> getAllColorsB() {
        return ResponseEntity.ok(this.colorBService.findAllValidColors());
    }
}
