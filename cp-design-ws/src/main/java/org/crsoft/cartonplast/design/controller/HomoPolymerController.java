package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.HomoPolymerService;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;
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
@RequestMapping(V1_API_VERSION + "/homopolymers")
public class HomoPolymerController {

    private final HomoPolymerService homoPolymerService;

    public HomoPolymerController(HomoPolymerService homoPolymerService) {
        this.homoPolymerService = homoPolymerService;
    }

    @GetMapping
    public ResponseEntity<List<HomoPolymerRes>> getAllHomoPolymers() {
        return ResponseEntity.ok(this.homoPolymerService.findAllValidHomopolymers());
    }
}
