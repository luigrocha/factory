package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.vo.req.DieProductReq;
import org.crsoft.cartonplast.vo.res.DieProductRes;
import org.crsoft.cartonplast.design.service.impl.DieProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 12/06/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/die-products")
public class DieProductController {

    private final DieProductService dieProductService;

    @GetMapping
    public ResponseEntity<List<DieProductRes>> findAllValidDieProducts() {
        return ResponseEntity.ok(dieProductService.findAllValidDieProducts());
    }

    @GetMapping("/available")
    public ResponseEntity<List<DieProductRes>> findAllAvailableDieProducts() {
        return ResponseEntity.ok(dieProductService.findAllAvailableDieProducts());
    }

    @PostMapping
    public ResponseEntity<DieProductRes> createDieProduct(
            @Valid @RequestBody DieProductReq dieProductReq) {
        return ResponseEntity.ok(dieProductService.save(dieProductReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DieProductRes> updateDieProduct(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DieProductReq dieProductReq) {
        return ResponseEntity.ok(dieProductService.update(id, dieProductReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDieProduct(
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(dieProductService.delete(id));
    }
}
