package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.ThicknessService;
import org.crsoft.cartonplast.design.vo.res.ThicknessRes;
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
@RequestMapping(V1_API_VERSION + "/thicknesses")
public class ThicknessController {

    private final ThicknessService thicknessService;

    public ThicknessController(ThicknessService thicknessService) {
        this.thicknessService = thicknessService;
    }

    @GetMapping
    public ResponseEntity<List<ThicknessRes>> getAllThicknesses() {
        return ResponseEntity.ok(this.thicknessService.findAllValidThickness());
    }
}
