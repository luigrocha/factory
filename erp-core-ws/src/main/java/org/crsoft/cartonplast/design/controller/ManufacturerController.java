package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Manufacturer;
import org.crsoft.cartonplast.design.service.IManufacturerService;
import org.crsoft.cartonplast.vo.req.ManufacturerReq;
import org.crsoft.cartonplast.vo.res.ManufacturerRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 14/06/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final IManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerRes>> findAllManufacturers() throws NotFoundException {
        return ResponseEntity.ok(manufacturerService.findAllValidManufacturers());
    }

    @PostMapping
    public ResponseEntity<Manufacturer> createManufacturer(@RequestBody ManufacturerReq manufacturerReq) throws InsertException {
        this.manufacturerService.createManufacturer(manufacturerReq);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{code}")
    public ResponseEntity<ManufacturerRes> updateManufacturer(@PathVariable Integer code, @RequestBody ManufacturerReq manufacturerReq) throws NotFoundException, UpdateException {
        this.manufacturerService.updateManufacturerByCode(code, manufacturerReq);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ManufacturerRes> deleteManufacturerByCode(@PathVariable Integer code) throws NotFoundException, UpdateException {
        this.manufacturerService.deleteManufacturerByCode(code);
        return ResponseEntity.ok().build();
    }
}
