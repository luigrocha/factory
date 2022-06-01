package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.DivisionService;
import org.crsoft.cartonplast.users.vo.req.DivisionReq;
import org.crsoft.cartonplast.users.vo.res.DivisionRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 31/05/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/divisions")
public class DivisionController {

    private final DivisionService divisionService;

    @GetMapping
    public ResponseEntity<List<DivisionRes>> findAllDivisions() {
        return ResponseEntity.ok(divisionService.findAllValidDivisions());
    }

    @PostMapping
    public ResponseEntity<DivisionRes> createDivision(
            @Valid @RequestBody DivisionReq divisionReq) {
        return ResponseEntity.ok(divisionService.save(divisionReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DivisionRes> updateDivision(
            @PathVariable("id") String id,
            @Valid @RequestBody DivisionReq divisionReq) {
        return ResponseEntity.ok(divisionService.update(id, divisionReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDivision(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(divisionService.delete(id));
    }
}
