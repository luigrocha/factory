package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.design.service.impl.ThicknessService;
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
    public ResponseEntity<Collection<ThicknessRes>> getAllThicknesses() throws NotFoundException {
        return ResponseEntity.ok(this.thicknessService.findAllValidThickness());
    }

    @PostMapping
    public ResponseEntity<?> createThickness(@RequestBody Thickness thickness, @RequestHeader("userName") String userName) throws InsertException {
        thickness.setCreatedBy(userName);
        this.thicknessService.createThickness(thickness);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<ThicknessRes> findThicknessByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok().body(this.thicknessService.findThicknessByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateThicknessByCode(@PathVariable("code") Integer code, @RequestBody Thickness thickness, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        thickness.setUpdatedBy(userName);
        this.thicknessService.updateThicknessByCode(code, thickness);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteThicknessByCode(@PathVariable("code") Integer code, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        this.thicknessService.deleteThicknessByCode(code, userName);
        return ResponseEntity.ok().build();
    }

}
