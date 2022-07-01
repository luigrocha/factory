package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.ColorTypeService;
import org.crsoft.cartonplast.vo.res.ColorTypeRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 17/06/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(V1_API_VERSION + "/color-types")
public class ColorTypeController {

    private final ColorTypeService colorTypeService;

    @GetMapping
    public ResponseEntity<List<ColorTypeRes>> getAllColorTypes() {
        return ResponseEntity.ok(this.colorTypeService.findAllValidColorTypes());
    }
}
