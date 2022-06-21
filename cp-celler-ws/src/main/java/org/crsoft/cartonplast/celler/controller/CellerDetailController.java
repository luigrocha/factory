package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.util.HttpUtil;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
/*@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/celler")*/
public class CellerDetailController {
    private final ICellerService cellerService;

    public CellerDetailController(ICellerService cellerService) {
        this.cellerService = cellerService;
    }

    @GetMapping
    public ResponseEntity<Collection<CellerRes>> findAllCeller() throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findAllCeller());
    }

/*
    @GetMapping("/findByMaterialCode/{code}")
    public ResponseEntity<Collection<CellerRes>> findCellerByMaterialCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findCellerByMaterialCode(code));
    }
*/

    @GetMapping("/findNewCodeDocumentByDocumentCode/{code}")
    public ResponseEntity<CodeDocumentRes> findNewCodeDocumentByDocumentCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.cellerService.findNewCodeDocumentByDocumentCode(code));
    }

    @PostMapping
    public ResponseEntity<?> createCeller(@RequestBody Celler celler, @RequestHeader("userName") String userName) throws InsertException, NotFoundException {
        celler.setUpdatedBy(userName);
        this.cellerService.createCeller(celler);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/generate-receipt", produces = "application/pdf")
    public ResponseEntity<byte[]> generateReceipt(
            @Valid @RequestBody GenerateReceiptReq generateReceiptReq,
            @RequestParam("documentId") Integer documentId) throws NotFoundException {
        byte[] pdf = cellerService.generateReceipt(generateReceiptReq, documentId);
        return new ResponseEntity<>(
                pdf,
                HttpUtil.getDefaultPDFHeaders(generateReceiptReq.getReceiptNumber()),
                HttpStatus.OK);
    }
}
