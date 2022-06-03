package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;
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
@RequestMapping(GlobalConstant.V1_API_VERSION + "/celler")
public class CellerController {

    private final ICellerService cellerService;

    public CellerController(ICellerService cellerService) {
        this.cellerService = cellerService;
    }

    @GetMapping
    public ResponseEntity<Collection<CellerRes>> findAllCeller() throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findAllCeller());
    }

    @GetMapping("/findByMaterialCode/{code}")
    public ResponseEntity<Collection<CellerRes>> findCellerByMaterialCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findCellerByMaterialCode(code));
    }

    @GetMapping("/findNewCodeDocumentByDocumentCode/{code}")
    public ResponseEntity<CodeDocumentRes> findNewCodeDocumentByDocumentCode(@PathVariable("code")Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findNewCodeDocumentByDocumentCode(code));
    }
}
