package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.HomoPolymerService;
import org.crsoft.cartonplast.vo.req.HomoPolymerReq;
import org.crsoft.cartonplast.vo.res.HomoPolymerRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/homopolymers")
@RequiredArgsConstructor
public class HomoPolymerController {

    private final HomoPolymerService homoPolymerService;

    @GetMapping
    public ResponseEntity<Collection<HomoPolymerRes>> getAllHomoPolymers() {
        return ResponseEntity.ok(this.homoPolymerService.findAllValidHomopolymers());
    }

    @PostMapping
    public ResponseEntity<HomoPolymerRes> createHomoPolymer(
            @Valid @RequestBody HomoPolymerReq homoPolymerReq) {
        return ResponseEntity.ok(this.homoPolymerService.createHomopolymer(homoPolymerReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<HomoPolymerRes> findHomoPolymerByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.homoPolymerService.findHomoPolymerByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<HomoPolymerRes> updateHomoPolymerByCode(
            @PathVariable("code") Integer code,
            @Valid @RequestBody HomoPolymerReq homoPolymerReq) {
        return ResponseEntity.ok().body(this.homoPolymerService.updateHomoPolymerByCode(code, homoPolymerReq));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deleteHomoPolymerByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.homoPolymerService.deleteHomoPolymerByCode(code));
    }
}
