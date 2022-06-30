package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.service.ILocationService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/location")
public class LocationController {

    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Collection<LocationRes>> findAllLocation() throws NotFoundException {
        return ResponseEntity.ok(this.locationService.findAllLocation());
    }

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody Location location, @RequestHeader("userName") String userName) throws InsertException {
        location.setCreatedBy(userName);
        this.locationService.createLocation(location);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<LocationRes> findLocationByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.locationService.findLocationByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateLocationByCode(
            @PathVariable("code") Integer code,
            @RequestBody Location location,
            @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        location.setUpdatedBy(userName);
        this.locationService.updateLocationByCode(code, location);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteLocationByCode(@PathVariable("code") Integer code, @RequestHeader("userName") String userName)
            throws NotFoundException, UpdateException {
        this.locationService.deleteLocationByCode(code, userName);
        return ResponseEntity.ok().build();
    }

}
