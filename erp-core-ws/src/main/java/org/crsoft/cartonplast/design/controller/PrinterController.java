package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.PrinterService;
import org.crsoft.cartonplast.vo.req.PrinterReq;
import org.crsoft.cartonplast.vo.res.PrinterRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/printers")
@RequiredArgsConstructor
public class PrinterController {

    private final PrinterService printerService;

    @GetMapping
    public ResponseEntity<Collection<PrinterRes>> getAllPrinters() {
        return ResponseEntity.ok(this.printerService.findAllValidPrinters());
    }

    @PostMapping
    public ResponseEntity<PrinterRes> createPrinter(
            @Valid @RequestBody PrinterReq printerReq) {
        return ResponseEntity.ok(this.printerService.createPrinter(printerReq));
    }

    @GetMapping("/{code}")
    public ResponseEntity<PrinterRes> findPrinterByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.printerService.findPrinterByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<PrinterRes> updatePrinterByCode(
            @PathVariable("code") Integer code,
            @Valid @RequestBody PrinterReq printerReq) {
        return ResponseEntity.ok().body(this.printerService.updatePrinterByCode(code, printerReq));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> deletePrinterByCode(
            @PathVariable("code") Integer code) {
        return ResponseEntity.ok().body(this.printerService.deletePrinterByCode(code));
    }

}
