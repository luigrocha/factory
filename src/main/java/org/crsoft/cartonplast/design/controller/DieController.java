package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.vo.req.DieReq;
import org.crsoft.cartonplast.vo.res.DieRes;
import org.crsoft.cartonplast.design.service.impl.DieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 30/04/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/dies")
public class DieController {

    private final DieService dieService;

    @GetMapping
    public ResponseEntity<List<DieRes>> findAllValidDies() {
        return ResponseEntity.ok(dieService.findAllValidDies());
    }

    @PostMapping
    public ResponseEntity<DieRes> createDie(
            @Valid @RequestBody DieReq dieReq) {
        return ResponseEntity.ok(dieService.save(dieReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DieRes> updateDie(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DieReq dieReq) {
        return ResponseEntity.ok(dieService.update(id, dieReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDie(
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(dieService.delete(id));
    }
}
