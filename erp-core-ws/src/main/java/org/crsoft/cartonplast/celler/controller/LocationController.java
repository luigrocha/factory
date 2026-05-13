package org.crsoft.cartonplast.celler.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.celler.service.ILocationService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.LocationReq;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    @GetMapping
    public ResponseEntity<Collection<LocationRes>> findAllLocation() {
        return ResponseEntity.ok(this.locationService.findAllLocation());
    }

    @PostMapping
    public ResponseEntity<LocationRes> createLocation(
            @Valid @RequestBody LocationReq locationReq) {
        return ResponseEntity.ok(this.locationService.createLocation(locationReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<LocationRes> findLocationByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.locationService.findLocationByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<LocationRes> updateLocationByCode(
            @PathVariable("code") Integer code,
            @Valid @RequestBody LocationReq locationReq) {
        return ResponseEntity.ok(this.locationService.updateLocationByCode(code, locationReq));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deleteLocationByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok(this.locationService.deleteLocationByCode(code));
    }

}
