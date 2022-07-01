package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.ManufacturerService;
import org.crsoft.cartonplast.vo.res.ManufacturerRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 14/06/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerRes>> findAllManufacturers() {
        return ResponseEntity.ok(manufacturerService.findAllValidManufacturers());
    }
}
