package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.ICellerDetailService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/cellerDetail")
public class CellerDetailController {
    private final ICellerDetailService cellerDetailService;

    public CellerDetailController(ICellerDetailService cellerDetailService) {
        this.cellerDetailService = cellerDetailService;
    }

    @GetMapping("/findByLocationCode/{codeLocation}/{codeMaterial}")
    public ResponseEntity<Collection<CellerDetailRes>> findByLocationCode(
            @PathVariable("codeLocation") Integer codeLocation,
            @PathVariable("codeMaterial") Integer codeMaterial) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findByLocationCode(codeLocation, codeMaterial));
    }

    @GetMapping("/findByMaterialCode/{code}")
    public ResponseEntity<Collection<CellerDetailRes>> findCellerByMaterialCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findCellerDetailByMaterialCode(code));
    }

    @GetMapping("/findByCellerCode/{code}")
    public ResponseEntity<Collection<CellerDetailRes>> findCellerDetailByCellerCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findCellerDetailByCellerCode(code));
    }
}
