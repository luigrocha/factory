package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.service.impl.HomoPolymerService;
import org.crsoft.cartonplast.vo.res.HomoPolymerRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public ResponseEntity<Collection<HomoPolymerRes>> getAllHomoPolymers() throws NotFoundException {
        return ResponseEntity.ok(this.homoPolymerService.findAllValidHomopolymers());
    }

    @PostMapping
    public ResponseEntity<?> createHomoPolymer(@RequestBody HomoPolymer homoPolymer, @RequestHeader("userName") String userName) throws InsertException {
        homoPolymer.setCreatedBy(userName);
        this.homoPolymerService.createHomopolymer(homoPolymer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<HomoPolymerRes> findHomoPolymerByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok().body(this.homoPolymerService.findHomoPolymerByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updateHomoPolymerByCode(@PathVariable("code") Integer code, @RequestBody HomoPolymer homoPolymer, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        homoPolymer.setUpdatedBy(userName);
        this.homoPolymerService.updateHomoPolymerByCode(code, homoPolymer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteHomoPolymerByCode(@PathVariable("code") Integer code, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        this.homoPolymerService.deleteHomoPolymerByCode(code, userName);
        return ResponseEntity.ok().build();
    }

}
