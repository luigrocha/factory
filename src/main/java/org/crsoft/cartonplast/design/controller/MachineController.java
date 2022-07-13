package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.MachineService;
import org.crsoft.cartonplast.vo.req.MachineReq;
import org.crsoft.cartonplast.vo.res.MachineCatalogRes;
import org.crsoft.cartonplast.vo.res.MachineRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 09/06/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/machines")
public class MachineController {

    private final MachineService machineService;

    @GetMapping
    public ResponseEntity<List<MachineRes>> findAllMachines() {
        return ResponseEntity.ok(machineService.findAllValidMachines());
    }

    @PostMapping
    public ResponseEntity<MachineRes> createMachine(
            @Valid @RequestBody MachineReq machineReq) {
        return ResponseEntity.ok(machineService.save(machineReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineRes> updateMachine(
            @PathVariable("id") Integer id,
            @Valid @RequestBody MachineReq machineReq) {
        return ResponseEntity.ok(machineService.update(id, machineReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteMachine(
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(machineService.delete(id));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<Collection<MachineCatalogRes>> findAllMachinesCatalog(){
        return ResponseEntity.ok(machineService.findAllValidMachinesCatalog());
    }
}
