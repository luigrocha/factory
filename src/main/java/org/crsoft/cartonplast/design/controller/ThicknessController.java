package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.ThicknessService;
import org.crsoft.cartonplast.vo.req.ThicknessReq;
import org.crsoft.cartonplast.vo.res.ThicknessRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public ResponseEntity<Collection<ThicknessRes>> getAllThicknesses() {
        return ResponseEntity.ok(this.thicknessService.findAllValidThickness());
    }

    @PostMapping
    public ResponseEntity<ThicknessRes> createThickness(
            @RequestBody ThicknessReq thicknessReq) {
        return ResponseEntity.ok(this.thicknessService.createThickness(thicknessReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<ThicknessRes> findThicknessByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.thicknessService.findThicknessByCode(code));
    }

    @PutMapping("/{code}")
    public ResponseEntity<ThicknessRes> updateThicknessByCode(
            @PathVariable("code") Integer code,
            @RequestBody ThicknessReq thicknessReq) {
        return ResponseEntity.ok().body(this.thicknessService.updateThicknessByCode(code, thicknessReq));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deleteThicknessByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.thicknessService.deleteThicknessByCode(code));
    }

}
