package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.EthnicService;
import org.crsoft.cartonplast.users.vo.req.EthnicReq;
import org.crsoft.cartonplast.users.vo.res.EthnicRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 30/05/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/ethnics")
public class EthnicController {

    private final EthnicService ethnicService;

    @GetMapping
    public ResponseEntity<List<EthnicRes>> findAllEthnics() {
        return ResponseEntity.ok(ethnicService.findAllValidEthnics());
    }

    @PostMapping
    public ResponseEntity<EthnicRes> createEthnic(
            @Valid @RequestBody EthnicReq ethnicReq) {
        return ResponseEntity.ok(ethnicService.save(ethnicReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EthnicRes> updateEthnic(
            @PathVariable("id") String id,
            @Valid @RequestBody EthnicReq ethnicReq) {
        return ResponseEntity.ok(ethnicService.update(id, ethnicReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEthnic(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(ethnicService.delete(id));
    }
}
